package com.sysongy.api.util;

import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sysongy.api.umeng.push.AndroidNotification;
import com.sysongy.api.umeng.push.PushClient;
import com.sysongy.api.umeng.push.android.AndroidBroadcast;
import com.sysongy.api.umeng.push.android.AndroidCustomizedcast;
import com.sysongy.api.umeng.push.android.AndroidFilecast;
import com.sysongy.api.umeng.push.android.AndroidGroupcast;
import com.sysongy.api.umeng.push.android.AndroidUnicast;
import com.sysongy.api.umeng.push.ios.IOSBroadcast;
import com.sysongy.api.umeng.push.ios.IOSCustomizedcast;
import com.sysongy.api.umeng.push.ios.IOSFilecast;
import com.sysongy.api.umeng.push.ios.IOSGroupcast;
import com.sysongy.api.umeng.push.ios.IOSUnicast;
import com.sysongy.api.umeng.push.model.CommonParams;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;

public class UmengUtil {

	private String appkey = null;
	private String appMasterSecret = null;
	private String timestamp = null;
	private PushClient client = new PushClient();
	public Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
	public UmengUtil(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public int sendAndroidBroadcast(CommonParams params) throws Exception {

		AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
		if ("true".equalsIgnoreCase((String) prop.get("umengMode"))) {
			broadcast.setProductionMode();//设置为正常模式
		} else {
			broadcast.setTestMode();//设置为测试模式
		}
		broadcast.setTicker(params.getTicker());
		broadcast.setTitle(params.getTitle());
		broadcast.setText(params.getText());
		
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		broadcast.goActivityAfterOpen("com.sysongy.main.MessageDialogActivity");// 设置弹出
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
//		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("content", params.getContent());// 弹出内容
		broadcast.setExtraField("title", params.getTitle());// 设置自定义title
		return client.send(broadcast);
	}

	public int sendAndroidUnicast(CommonParams params) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
		// TODO Set your device token
		if ("true".equalsIgnoreCase((String) prop.get("umengMode"))) {
			unicast.setProductionMode();//s设置为正常模式
		} else {
			unicast.setTestMode();//设置为测试模式
		}
		unicast.setDeviceToken(params.getDevice_tokens());
		unicast.setTicker(params.getTicker());
		unicast.setTitle(params.getTitle());
		unicast.setText(params.getText());
		
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		unicast.goActivityAfterOpen("com.sysongy.main.MessageDialogActivity");// 设置弹出

		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
//		unicast.setProductionMode();
		// Set customized fields

		unicast.setExtraField("content", params.getContent());// 弹出内容
		unicast.setExtraField("title", params.getTitle());//设置自定义title
		return client.send(unicast);
	}

	public int sendAndroidGroupcast(CommonParams params) throws Exception {
		
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"test"}, {"tag":"Test"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("province", params.getProvince());
//		TestTag.put("tag", "Test");
		tagArray.put(testTag);
//		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		if ("true".equalsIgnoreCase((String) prop.get("umengMode"))) {
			groupcast.setProductionMode();//设置为正常模式
		} else {
			groupcast.setTestMode();//设置为测试模式
		} 

		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		groupcast.goActivityAfterOpen("com.sysongy.main.MessageDialogActivity");// 设置弹出
		groupcast.setFilter(filterJson);
		groupcast.setTicker(params.getTicker());
		groupcast.setTitle(params.getTitle());
		groupcast.setText(params.getText());
		groupcast.setExtraField("content", params.getContent());// 弹出内容
		groupcast.setExtraField("title", params.getTitle());//设置自定义title
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
//		groupcast.setProductionMode(); //设置正式模式
		return client.send(groupcast);
	}

	public void sendAndroidCustomizedcast() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidCustomizedcastFile() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker("Android customizedcast ticker");
		customizedcast.setTitle("中文的title");
		customizedcast.setText("Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}

	public void sendAndroidFilecast() throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setTicker("Android filecast ticker");
		filecast.setTitle("中文的title");
		filecast.setText("Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}

	public void sendIOSBroadcast() throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey, appMasterSecret);

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge(0);
		broadcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}

	public void sendIOSUnicast() throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken("xx");
		unicast.setAlert("IOS 单播测试");
		unicast.setBadge(0);
		unicast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		unicast.setTestMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}

	public void sendIOSGroupcast() throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"iostest"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge(0);
		groupcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		groupcast.setTestMode();
		client.send(groupcast);
	}

	public void sendIOSCustomizedcast() throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them
		// if there are multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge(0);
		customizedcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		customizedcast.setTestMode();
		client.send(customizedcast);
	}

	public void sendIOSFilecast() throws Exception {
		IOSFilecast filecast = new IOSFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge(0);
		filecast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		filecast.setTestMode();
		client.send(filecast);
	}

	public static void main(String[] args) {
		// TODO set your appkey and master secret here
		UmengUtil demo = new UmengUtil("5782f28567e58ed4eb000f60", "rf0fxd7hpdr399ds5lx0qtuio8cm6ail");
		try {
			CommonParams params = new CommonParams();
			params.setDevice_tokens("");
			params.setTicker("");
			params.setTitle("");
			params.setText("");
			demo.sendAndroidUnicast(params);
			/*
			 * TODO these methods are all available, just fill in some fields
			 * and do the test demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast(); demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast(); demo.sendAndroidFilecast();
			 *
			 * demo.sendIOSBroadcast(); demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast(); demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
