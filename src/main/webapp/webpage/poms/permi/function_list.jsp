<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
</script>
<div class="page-header">
	<h1>
		功能管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			功能列表 &amp;
		</small>
	</h1>
</div><!-- /.page-header -->
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="row">
			<div class="col-xs-12">
				<table id="simple-table" class="table table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th class="center">
							<label class="pos-rel">
								<input type="checkbox" class="ace" />
								<span class="lbl"></span>
							</label>
						</th>
						<th>账号</th>
						<th>姓名</th>
						<th class="hidden-480">性别</th>
						<th>邮箱</th>
						<th class="hidden-480">联系电话</th>
						<th>用户角色</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td class="center">
							<label class="pos-rel">
								<input type="checkbox" class="ace" />
								<span class="lbl"></span>
							</label>
						</td>
						<td>
							<a href="#">ace.com</a>
						</td>
						<td>$45</td>
						<td>$45</td>
						<td>$45</td>
						<td class="hidden-480">3,330</td>
						<td>Feb 12</td>
						<td class="hidden-480">
						</td>
						<td>
							<a>修改</a> <a>详情</a> <a>删除</a>
						</td>
					</tr>
					</tbody>
				</table>
			</div><!-- /.span -->
		</div><!-- /.row -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

