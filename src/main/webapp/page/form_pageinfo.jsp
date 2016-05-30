<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<input id="pageNum" type="hidden" name="pageNum" value="${pageinfo.pageNum}" />
<input id="pageSize" type="hidden" name="pageSize" value="10" />
<input id="pageNumMax" type="hidden" name="pageNumMax" value="${pageinfo.pages}" />
<input id="total" type="hidden" name="total" value=" ${pageinfo.total}" />
<input id="orderby" type="hidden" name="orderby" value="${pageinfo.orderBy}" />
<input id="retCode" type="hidden" value=" ${ret.retCode}" />
<input id="retMsg" type="hidden" value=" ${ret.retMsg}" />