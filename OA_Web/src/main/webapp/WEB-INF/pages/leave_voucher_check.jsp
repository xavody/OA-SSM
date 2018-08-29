<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="me.imtt.oa.global.Constants.ConstantLeaveVoucher" %>
<%@ page import="me.imtt.oa.global.Constants.ConstantPosts" %>
<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 处理请假单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">事由</div>
                        <div class="col-md-6">${leaveVoucher.cause}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">创建人</div>
                        <div class="col-md-4">${leaveVoucher.creator.name}</div>
                        <div class="col-md-2">创建时间</div>
                        <div class="col-md-4"><spring:eval expression="leaveVoucher.createTime"/></div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">待处理人</div>
                        <div class="col-md-4">${leaveVoucher.dealer.name}</div>
                        <div class="col-md-2">状态</div>
                        <div class="col-md-4">${leaveVoucher.status}</div>
                    </div>
                    <div class="section row">
                        <div class="col-md-2">请假天数</div>
                        <div class="col-md-6">${leaveVoucher.totalDays}</div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 处理流程 </span>
                    </div>
                    <div class="section row">
                        <c:forEach items="${records}" var="record">
                            <div class="col-md-1">${record.dealer.name}</div>
                            <div class="col-md-3"><spring:eval expression="record.dealTime"/></div>
                            <div class="col-md-1">${record.dealWay}</div>
                            <div class="col-md-2">${record.dealWay}</div>
                            <div class="col-md-5">备注：${record.comment}</div>
                        </c:forEach>
                    </div>
                    <form:form id="admin-form" name="addForm" action="/leave_voucher/checkTo" modelAttribute="record">
                        <form:hidden path="leaveVoucherId"/>
                        <div class="panel-body bg-light">
                            <div class="section">
                                <label for="comment" class="field prepend-icon">
                                    <form:input path="comment" cssClass="gui-input" placeholder="备注..."/>
                                    <label for="comment" class="field-icon">
                                        <i class="fa fa-lock"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="panel-footer text-right">
                                <c:if test="${sessionScope.employee.post==ConstantPosts.POST_FM || sessionScope.employee.post==ConstantPosts.POST_GM}">
                                    <c:if test="${leaveVoucher.status!=ConstantLeaveVoucher.Leave_VOUCHER_APPROVED}">
                                        <button type="submit" class="button" name="dealWay"
                                                value="${ConstantLeaveVoucher.DEAL_APPROVE}">${ConstantLeaveVoucher.DEAL_APPROVE}</button>
                                        <button type="submit" class="button" name="dealWay"
                                                value="${ConstantLeaveVoucher.DEAL_BACK}">${ConstantLeaveVoucher.DEAL_BACK}</button>
                                        <button type="submit" class="button" name="dealWay"
                                                value="${ConstantLeaveVoucher.DEAL_REJECT}">${ConstantLeaveVoucher.DEAL_REJECT}</button>
                                    </c:if>
                                </c:if>
                                <c:if test="${sessionScope.employee.post==ConstantPosts.POST_FM
                                && sessionScope.employee.department.name=='人事部'
                                && leaveVoucher.status==ConstantLeaveVoucher.Leave_VOUCHER_APPROVED}">
                                    <button type="submit" class="button" name="dealWay"
                                            value="${ConstantLeaveVoucher.DEAL_CONFIRM}">${ConstantLeaveVoucher.DEAL_CONFIRM}</button>
                                </c:if>
                                <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回
                                </button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>
