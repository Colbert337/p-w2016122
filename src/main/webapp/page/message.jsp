<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="modal-table" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header no-padding">
							<div class="table-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">
									<span class="white">&times;</span>
								</button>
								Return Code =  ${ret.retCode}
							</div>
						</div>

						<div class="modal-body no-padding">
							<br/>
							${ret.retMsg}"
						</div>

						<div class="modal-footer no-margin-top">
							
							<button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
								<i class="ace-icon fa fa-times"></i> Close
							</button>
							 
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>