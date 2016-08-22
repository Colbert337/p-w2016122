package com.sysongy.poms.liquid.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.base.model.CurrUser;
import com.sysongy.poms.base.model.PageBean;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.service.SysDepositLogService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;

import jxl.Sheet;
import jxl.Workbook;

@RequestMapping("/web/gastationMap")
@Controller
public class GastationMapController extends BaseContoller {

	@Autowired
	private GastationService service;
	@Autowired
	private SysDepositLogService depositLogService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrderService orderService;

	private Gastation gastation;

	Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);

	@RequestMapping("/gastationMapList")
	public String queryGastationMapList2(ModelMap map, Gastation gastation) throws Exception {

		this.gastation = gastation;
		PageBean bean = new PageBean();
		String ret = "webpage/poms/gastation/gastationMap_list";

		try {
			if (gastation.getPageNum() == null) {
				gastation.setPageNum(1);
				gastation.setPageSize(10);
			}
			if (StringUtils.isEmpty(gastation.getOrderby())) {
				gastation.setOrderby("created_time desc");
			}

			PageInfo<Gastation> pageinfo = service.queryGastation2(gastation);

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			map.addAttribute("ret", bean);
			map.addAttribute("pageInfo", pageinfo);
			map.addAttribute("gastation", gastation);
		} catch (Exception e) {
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());

			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			return ret;
		}
	}

	@RequestMapping("/execlDownload")
	public String execlDownload(ModelMap map, HttpServletResponse response,
			@ModelAttribute("currUser") CurrUser currUser) throws IOException {
		String ret = "";
		OutputStream os = response.getOutputStream();
		PageBean bean = new PageBean();
		try {

			bean.setRetCode(100);
			bean.setRetMsg("查询成功");
			bean.setPageInfo(ret);

			response.setHeader("Content-Disposition", "attachment; filename=gastation_temp.xls");
			response.setContentType("application/octet-stream; charset=utf-8");
			os.write(FileUtils.readFileToByteArray(new File((String) prop.get("gastation_temp"))));
		} catch (Exception e) {
			// TODO: handle exception
			bean.setRetCode(5000);
			bean.setRetMsg(e.getMessage());
			map.addAttribute("ret", bean);
			logger.error("", e);
			throw e;
		} finally {
			os.close();
			return null;
		}

	}
/**
 * execl 导入数据
 * @param file
 * @param currUser
 * @param response
 * @return
 * @throws IOException
 */
	@RequestMapping("/file")
	public String file(@RequestParam("fileImport") MultipartFile file, @ModelAttribute("currUser") CurrUser currUser,
			HttpServletResponse response) throws IOException {
		String resultStr = "";
		String rsultPath = "redirect:/web/gastationMap/gastationMapList?resultInt=1";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String stationId = currUser.getStationId();
		List<Gastation> list = new ArrayList<>();
		String message = "";
		PageBean bean = new PageBean();
		PrintWriter pw = response.getWriter();
		int count = 0;
		int update = 0;
		try {

			if (file != null && !"".equals(file)) {
				Workbook book = Workbook.getWorkbook(file.getInputStream());
				Sheet sheet = book.getSheet(0);
				int rows = sheet.getRows();
				int columns = sheet.getColumns();
				int err = 0;
				//循环从2（第三行开示） 前两行是表头
				for (int i =2; i < rows; i++) {

					Integer gender = 0;
					String platesNumber = "";
					if (sheet.getCell(0, i) != null && !"".equals(sheet.getCell(0, i))) {
						Gastation gas = new Gastation();
						if (sheet.getCell(0, i).getContents() != null
								&& !"".equals(sheet.getCell(0, i).getContents().replace(" ", ""))) {
							gas.setSys_gas_station_id(sheet.getCell(0, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第1列（加注站编号不能为空）\n";
							err++;
							continue;
						}

						if (sheet.getCell(1, i) != null
								&& !"".equals(sheet.getCell(1, i).getContents().replace(" ", ""))) {
							gas.setGas_station_name(sheet.getCell(1, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第2列（加注站名称不能为空）\n";
							err++;
							continue;
						}
						if (sheet.getCell(2, i) != null && !"".equals(sheet.getCell(2, i))) {
							gas.setPlatform_type(sheet.getCell(2, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第3列（加注站类型不能为空）\n";
							err++;
							continue;
						}
						if (sheet.getCell(3, i) != null
								&& !"".equals(sheet.getCell(3, i).getContents().replace(" ", ""))) {
							gas.setProvince_id(sheet.getCell(3, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第4列（加注站所在省不能为空）\n";
							err++;
							continue;
						}
						if (sheet.getCell(4, i) != null
								&& !"".equals(sheet.getCell(4, i).getContents().replace(" ", ""))) {
							gas.setCity_id(sheet.getCell(4, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第5列（加注站所在市不能为空）\n";
							err++;
							continue;
						}
						if (sheet.getCell(5, i) != null
								&& !"".equals(sheet.getCell(5, i).getContents().replace(" ", ""))) {
							gas.setArea_id(sheet.getCell(5, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第6列（加注站所在区不能为空）\n";
							err++;
							continue;
						}
						if (sheet.getCell(6, i) != null
								&& !"".equals(sheet.getCell(6, i).getContents().replace(" ", ""))) {
							gas.setAddress(sheet.getCell(3, i).getContents() + " " + sheet.getCell(4, i).getContents()
									+ " " + sheet.getCell(5, i).getContents() + " "
									+ sheet.getCell(6, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第7列（加注站地址不能为空）\n";
							err++;
							continue;
						}

						if (sheet.getCell(7, i) != null
								&& !"".equals(sheet.getCell(7, i).getContents().replace(" ", ""))
								&& (sheet.getCell(7, i).getContents().indexOf(",") != -1 || sheet.getCell(7, i).getContents().indexOf("，") != -1)) {
							sheet.getCell(7, i).getContents().replace("，", ",");
							String[] xy = sheet.getCell(7, i).getContents().split(",");
							gas.setLongitude(xy[0].replace(",", ""));
							gas.setLatitude(xy[1].replace(",", ""));
						} else {
							message += "第" + (i + 1) + "行第8列（加注站坐标不能为空）\n";
							err++;
							continue;
						}
						// 提供服务字段 目前空缺
						// if (sheet.getCell(8, i) != null
						// && !"".equals(sheet.getCell(8,
						// i).getContents().replace(" ", ""))) {
						// gas.setProvince_id(sheet.getCell(8,
						// i).getContents());
						// }
						//

						if (sheet.getCell(9, i) != null
								&& !"".equals(sheet.getCell(9, i).getContents().replace(" ", ""))) {
							gas.setContact_phone(sheet.getCell(9, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第10列（联系电话不能为空）\n";
							err++;
							continue;
						}

						// if (sheet.getCell(10,
						// i)!=null&&!"".equals(sheet.getCell(10,i).getContents().replace("
						// ", ""))) {
						// gas.setProvince_id(sheet.getCell(10,i).getContents());
						// }
						//
						// if (sheet.getCell(11,
						// i)!=null&&!"".equals(sheet.getCell(11,i).getContents().replace("
						// ", ""))) {
						// gas.setProvince_id(sheet.getCell(11,i).getContents());
						// }

						if (sheet.getCell(12, i) != null
								&& !"".equals(sheet.getCell(12, i).getContents().replace(" ", ""))) {
							switch (sheet.getCell(12, i).getContents()) {
							case "联盟站":
								gas.setType("0");
								break;
							case "已核实":
								gas.setType("1");
								break;
							case "未核实":
								gas.setType("2");
								break;
							case "合作站(微信红包站)":
							case "合作站":
								gas.setType("3");
							default:
								gas.setType("1");
								break;
							}
							gas.setType(sheet.getCell(12, i).getContents());
						} else {
							message += "第" + (i + 1) + "行第13列（合作类型不能为空）\n";
							err++;
							continue;
						}

						if (sheet.getCell(13, i) != null
								&& !"".equals(sheet.getCell(13, i).getContents().replace(" ", ""))) {
							gas.setStatus(sheet.getCell(13, i).getContents().equals("正常运营")?"1":"0");
						} else {
							message += "第" + (i + 1) + "行第14列（状态不能为空）\n";
							err++;
							continue;
						}
						//生成id
					
		 
						// 去重判断
						if (exists(gas)) {
							service.insert(gas);
							count++;
						} else {
							service.update(gas);
							update++;
						}

						// System.out.print(sheet.getCell(j,i).getContents());

					}
				}
				bean.setRetCode(100);
				message += "成功（" + count + "条），刷新（" + update + "条），错误（" + err + "条）";
				bean.setPageInfo(rsultPath);
				bean.setRetMsg(message);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bean.setRetCode(500);
			bean.setRetMsg(e.getMessage());
			logger.error("", e);
		} finally {
			response.setContentType("text/xml; charset=utf-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			pw.write("{\"SUCCESS\":true,\"datas\":\"" + message.replaceAll("\n", "<br/>") + "\"}");
			pw.close();
			return null;
		}

	}

	private boolean exists(Gastation gas) throws Exception {
		// TODO Auto-generated method stub
		return service.exists(gas.getLongitude()+","+gas.getLatitude());
	}
}
