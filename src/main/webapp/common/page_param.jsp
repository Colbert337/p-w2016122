<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<input id="pageNum" type="hidden" name="pageNum" value="${pageInfo.pageNum}" />
<input id="pageSize" type="hidden" name="pageSize" value="10" />
<input id="pageNumMax" type="hidden" name="pageNumMax" value="${pageInfo.pages}" />
<input id="total" type="hidden" name="total" value=" ${pageInfo.total}" />
<input id="orderby" type="hidden" name="orderby" value="${pageInfo.orderBy}" />
<input id="retCode" type="hidden" value=" ${ret.retCode}" />
<input id="retMsg" type="hidden" value=" ${ret.retMsg}" />