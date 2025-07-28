<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
request.setCharacterEncoding("UTF-8");

String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>대한항공 관리자 페이지 | 예약 관리</title>

<link href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/static/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/static/vendors/nprogress/nprogress.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/resources/static/build/css/custom.min.css" rel="stylesheet">


</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<%-- 사이드바 전체 --%>
				<jsp:include page="sidebar.jsp"></jsp:include>
			</div>

			<%-- 상단 네비게이션 전체 --%>
			<jsp:include page="topnav.jsp"></jsp:include>

			<%-- 메인 컨텐츠 --%>
			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>예약 관리</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<!-- 검색 패널 -->
					<div class="row">
						<div class="col-md-12 col-sm-12">
							<div class="x_panel">
								<div class="x_title">
									<h2>예약 검색</h2>
									<ul class="nav navbar-right panel_toolbox">
										<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
										<li><a class="close-link"><i class="fa fa-close"></i></a></li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label>사용자명으로 검색</label>
												<div class="input-group">
													<input type="text" class="form-control" id="reservationSearchInput" 
														   placeholder="사용자명을 입력하세요..." />
													<span class="input-group-btn">
														<button class="btn btn-primary" type="button" id="searchReservationBtn">
															<i class="fa fa-search"></i> 검색
														</button>
													</span>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<button type="button" class="btn btn-info" id="clearSearchBtn">
												<i class="fa fa-refresh"></i> 초기화
											</button>
											<button type="button" class="btn btn-success" id="loadAllReservationsBtn">
												<i class="fa fa-list"></i> 전체 예약 조회
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 검색 결과 패널 -->
					<div class="row">
						<div class="col-md-12 col-sm-12">
							<div class="x_panel">
								<div class="x_title">
									<h2>예약 목록</h2>
									<div class="pull-right">
										<span class="badge bg-blue" id="resultCount">0</span> 건의 검색 결과
									</div>
									<ul class="nav navbar-right panel_toolbox">
										<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
										<li><a class="close-link"><i class="fa fa-close"></i></a></li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<!-- 검색 결과 테이블 -->
									<div class="table-responsive">
										<table class="table table-striped table-bordered" id="reservationSearchResultTable" style="text-align: center;">
											<thead>
												<tr style="text-align: center;">
													<th style="text-align: center; vertical-align: middle;">예약번호</th>
													<th style="text-align: center; vertical-align: middle;">사용자명</th>
													<th style="text-align: center; vertical-align: middle;">이메일</th>
													<th style="text-align: center; vertical-align: middle;">연락처</th>
													<th style="text-align: center; vertical-align: middle;">출발지</th>
													<th style="text-align: center; vertical-align: middle;">도착지</th>
													<th style="text-align: center; vertical-align: middle;">출발일</th>
													<th style="text-align: center; vertical-align: middle;">예약일</th>
													<th style="text-align: center; vertical-align: middle;">상태</th>
													<th style="text-align: center; vertical-align: middle;">좌석 등급</th>
													<th style="text-align: center; vertical-align: middle;">승객 수</th>
													<th style="text-align: center; vertical-align: middle;">총 금액</th>
													<th style="text-align: center; vertical-align: middle;">관리</th>
												</tr>
											</thead>
											<tbody id="reservationTableBody" style="text-align: center; vertical-align: middle;">
												<!-- 검색 결과가 여기에 동적으로 추가됩니다 -->
											</tbody>
										</table>
									</div>
									

								</div>
							</div>
						</div>
					</div>
					
					<!-- 예약 상세 정보 모달 -->
					<div class="modal fade" id="reservationDetailModal" tabindex="-1" role="dialog">
						<div class="modal-dialog modal-lg" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">
										<span>&times;</span>
									</button>
									<h4 class="modal-title">
										<i class="fa fa-plane"></i> 예약 상세 정보
									</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<div class="col-md-6">
											<h5><i class="fa fa-info-circle"></i> 예약 정보</h5>
											<table class="table table-striped" style="text-align: center;">
												<tbody style="vertical-align: middle;">
													<tr>
														<td style="vertical-align: middle;"><strong>예약번호:</strong></td>
														<td style="vertical-align: middle;" id="modalReservationId">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>예약일:</strong></td>
														<td style="vertical-align: middle;" id="modalReservationDate">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>예약 상태:</strong></td>
														<td style="vertical-align: middle;">
															<span class="label" id="modalReservationStatus">-</span>
														</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>총 금액:</strong></td>
														<td style="vertical-align: middle;" id="modalTotalAmount">-</td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="col-md-6">
											<h5><i class="fa fa-user"></i> 예약자 정보</h5>
											<table class="table table-striped" style="text-align: center;">
												<tbody style="vertical-align: middle;">
													<tr>
														<td style="vertical-align: middle;"><strong>이름:</strong></td>
														<td style="vertical-align: middle;" id="modalUserName">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>이메일:</strong></td>
														<td style="vertical-align: middle;" id="modalUserEmail">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>연락처:</strong></td>
														<td style="vertical-align: middle;" id="modalUserPhone">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>생년월일:</strong></td>
														<td style="vertical-align: middle;" id="modalUserBirth">-</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									
									<!-- 항공편 정보 -->
									<div class="row" style="margin-top: 20px;">
										<div class="col-md-12">
											<h5><i class="fa fa-plane"></i> 항공편 정보</h5>
											<table class="table table-striped" style="text-align: center;">
												<tbody style="vertical-align: middle;">
													<tr>
														<td style="vertical-align: middle;"><strong>항공편명:</strong></td>
														<td style="vertical-align: middle;" id="modalFlightNumber">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>출발지:</strong></td>
														<td style="vertical-align: middle;" id="modalDeparture">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>도착지:</strong></td>
														<td style="vertical-align: middle;" id="modalArrival">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>출발일시:</strong></td>
														<td style="vertical-align: middle;" id="modalDepartureTime">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>도착일시:</strong></td>
														<td style="vertical-align: middle;" id="modalArrivalTime">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>좌석 등급:</strong></td>
														<td style="vertical-align: middle;" id="modalSeatClass">-</td>
													</tr>
													<tr>
														<td style="vertical-align: middle;"><strong>승객 수:</strong></td>
														<td style="vertical-align: middle;" id="modalPassengerCount">-</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-info" id="editReservationBtn">
										<i class="fa fa-edit"></i> 수정
									</button>
									<button type="button" class="btn btn-danger" id="deleteReservationBtn">
										<i class="fa fa-trash"></i> 삭제
									</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">
										<i class="fa fa-times"></i> 닫기
									</button>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 예약 삭제 확인 모달 -->
					<div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">
										<span>&times;</span>
									</button>
									<h4 class="modal-title">
										<i class="fa fa-warning text-danger"></i> 예약 삭제 확인
									</h4>
								</div>
								<div class="modal-body">
									<p>정말로 이 예약을 삭제하시겠습니까?</p>
									<p><strong>예약번호:</strong> <span id="deleteReservationId"></span></p>
									<p><strong>예약자:</strong> <span id="deleteReservationUser"></span></p>
									<div class="alert alert-warning">
										<i class="fa fa-warning"></i> 
										<strong>주의:</strong> 삭제된 예약은 복구할 수 없습니다.
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger" id="confirmDeleteBtn">
										<i class="fa fa-trash"></i> 삭제
									</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">
										<i class="fa fa-times"></i> 취소
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /page content -->

			<!-- footer content -->
			<footer>
				<div class="pull-right">
					Gentelella - Bootstrap Admin Template by <a
						href="https://colorlib.com">Colorlib</a>
				</div>
				<div class="clearfix"></div>
			</footer>
			<!-- /footer content -->
		</div>
	</div>

	<!-- jQuery -->
	<script src="${pageContext.request.contextPath}/resources/static/vendors/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
	<!-- FastClick -->
	<script src="${pageContext.request.contextPath}/resources/static/vendors/fastclick/lib/fastclick.js"></script>
	<!-- NProgress -->
	<script src="${pageContext.request.contextPath}/resources/static/vendors/nprogress/nprogress.js"></script>
	<!-- bootstrap-progressbar -->
	<script src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
	<!-- bootstrap-daterangepicker -->
	<script src="${pageContext.request.contextPath}/resources/static/vendors/moment/min/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

	<!-- Custom Theme Scripts -->
	<script src="${pageContext.request.contextPath}/resources/static/build/js/custom.js"></script>
	
	<!-- 예약 관리 JavaScript -->
	<script>
	$(document).ready(function() {

		// 페이지 로드 시 초기 예약 목록 출력
		function initializeReservationList() {
			console.log('=== 페이지 초기화 ===');
			displayServerReservations([]);
			updateResultCount(0);
		}



		// 서버 데이터 출력 함수 (서버에서 이미 페이지네이션 처리됨)
		function displayServerReservations(reservations) {
			const tbody = $('#reservationTableBody');
			tbody.empty();

			if (!reservations || reservations.length === 0) {
				tbody.append(`
					<tr>
						<td colspan="13" class="text-center">
							<i class="fa fa-info-circle"></i> 검색 결과가 없습니다.
						</td>
					</tr>
				`);
				return;
			}

			reservations.forEach(reservation => {
				// null/undefined 값 안전 처리 - null 값은 그대로 표시
				const statusLabel = getStatusLabel(reservation.status);
				const formattedAmount = reservation.totalAmount != null ? formatCurrency(reservation.totalAmount) : null;
				const departureDate = reservation.departureTime ? reservation.departureTime.split(' ')[0] : null;
				const reservationDate = reservation.reservationDate ? reservation.reservationDate.split(' ')[0] : null;
				
				tbody.append(`
					<tr>
						<td style="vertical-align: middle;">\${reservation.reservationId || null}</td>
						<td style="vertical-align: middle;">\${reservation.userName || null}</td>
						<td style="vertical-align: middle;">\${reservation.userEmail || null}</td>
						<td style="vertical-align: middle;">\${reservation.userPhone || null}</td>
						<td style="vertical-align: middle;">\${reservation.departure || null}</td>
						<td style="vertical-align: middle;">\${reservation.arrival || null}</td>
						<td style="vertical-align: middle;">\${departureDate || null}</td>
						<td style="vertical-align: middle;">\${reservationDate || null}</td>
						<td style="vertical-align: middle;">\${statusLabel}</td>
						<td style="vertical-align: middle;">\${reservation.seatClass || null}</td>
						<td style="vertical-align: middle;">\${reservation.passengerCount != null ? reservation.passengerCount + '명' : null}</td>
						<td style="vertical-align: middle;">\${formattedAmount || null}</td>
						<td style="vertical-align: middle;">
							<button class="btn btn-info btn-xs" onclick="showReservationDetail('\${reservation.reservationId || ''}')">
								<i class="fa fa-eye"></i> 상세
							</button>
						</td>
					</tr>
				`);
			});
		}

		// 상태에 따른 라벨 생성
		function getStatusLabel(status) {
			// null이나 undefined인 경우 null 반환
			if (status == null || status === '') {
				return null;
			}
			
			const statusMap = {
				'confirmed': '<span class="label label-success">확정</span>',
				'pending': '<span class="label label-warning">대기</span>',
				'cancelled': '<span class="label label-danger">취소</span>',
				'completed': '<span class="label label-info">완료</span>',
				'unknown': '<span class="label label-default">알 수 없음</span>'
			};
			return statusMap[status] || null;
		}

		// 원화 포맷팅 함수
		function formatCurrency(amount) {
			// null이나 undefined인 경우 null 반환
			if (amount == null) {
				return null;
			}
			return new Intl.NumberFormat('ko-KR', {
				style: 'currency',
				currency: 'KRW'
			}).format(amount);
		}

		// 결과 수 업데이트
		function updateResultCount(count) {
			$('#resultCount').text(count);
		}



		// 예약 검색 함수 (이름으로만 검색)
		function searchReservations() {
			const searchInput = $('#reservationSearchInput').val().trim();

			console.log('=== 이름 검색 시작 ===');
			console.log('검색어:', searchInput);

			// 검색 버튼 비활성화
			$('#searchReservationBtn').prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> 검색중...');

			// 요청 파라미터 구성
			const params = new URLSearchParams();
			if (searchInput) {
				params.append('searchKeyword', searchInput);
			}

			const requestUrl = '${pageContext.request.contextPath}/flight/reservationsearch?' + params.toString();
			console.log('요청 URL:', requestUrl);

			// 서버에 검색 요청
			fetch(requestUrl, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				}
			})
			.then(response => {
				console.log('서버 응답 상태:', response.status);
				if (!response.ok) {
					throw new Error('서버 응답 오류: ' + response.status);
				}
				return response.json();
			})
			.then(data => {
				console.log('서버 응답 데이터:', data);
				if (data.success) {
					const reservations = data.reservations || [];
					const totalCount = data.totalCount || 0;
					
					console.log(`받은 데이터 - 총 ${totalCount}건`);
					
					// 검색 결과 표시
					displayServerReservations(reservations);
					updateResultCount(totalCount);

					// 검색 결과 메시지
					if (totalCount === 0) {
						showNotification('검색 결과가 없습니다.', 'warning');
					} else {
						showNotification(`총 ${totalCount}건의 검색 결과를 찾았습니다.`, 'success');
					}
				} else {
					console.error('서버 응답 오류:', data.message);
					showNotification('검색 중 오류가 발생했습니다: ' + data.message, 'error');
					displayServerReservations([]);
					updateResultCount(0);
				}
			})
			.catch(error => {
				console.error('검색 오류:', error);
				console.error('오류 상세:', error.message);
				showNotification('서버 연동 오류가 발생했습니다: ' + error.message, 'error');
				displayServerReservations([]);
				updateResultCount(0);
			})
			.finally(() => {
				// 검색 버튼 재활성화
				$('#searchReservationBtn').prop('disabled', false).html('<i class="fa fa-search"></i> 검색');
			});
		}



		// 전체 예약 목록 조회 (단순화)
		function loadAllReservations() {
			// 검색 조건 초기화
			$('#reservationSearchInput').val('');

			console.log('=== 전체 목록 조회 시작 ===');

			// 전체 조회 버튼 비활성화
			$('#loadAllReservationsBtn').prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> 조회중...');

			// 서버에 전체 목록 요청 (빈 검색 조건으로 전체 조회)
			const requestUrl = '${pageContext.request.contextPath}/flight/reservationsearch';
			console.log('전체 조회 URL:', requestUrl);

			fetch(requestUrl, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				}
			})
			.then(response => {
				console.log('서버 응답 상태:', response.status);
				if (!response.ok) {
					throw new Error('서버 응답 오류: ' + response.status);
				}
				return response.json();
			})
			.then(data => {
				console.log('전체 목록 서버 응답:', data);
				if (data.success) {
					const reservations = data.reservations || [];
					const totalCount = data.totalCount || 0;
					
					console.log(`전체 목록 - 총 ${totalCount}건`);
					
					displayServerReservations(reservations);
					updateResultCount(totalCount);
					
					if (totalCount === 0) {
						showNotification('등록된 예약이 없습니다.', 'info');
					} else {
						showNotification(`전체 예약 목록을 조회했습니다. (총 ${totalCount}건)`, 'info');
					}
				} else {
					console.error('전체 목록 조회 오류:', data.message);
					showNotification('목록 조회 중 오류가 발생했습니다: ' + data.message, 'error');
					displayServerReservations([]);
					updateResultCount(0);
				}
			})
			.catch(error => {
				console.error('목록 조회 오류:', error);
				console.error('오류 상세:', error.message);
				showNotification('서버 연동 오류가 발생했습니다: ' + error.message, 'error');
				displayServerReservations([]);
				updateResultCount(0);
			})
			.finally(() => {
				// 버튼 재활성화
				$('#loadAllReservationsBtn').prop('disabled', false).html('<i class="fa fa-list"></i> 전체 예약 조회');
			});
		}



		// 검색 조건 초기화
		function clearSearch() {
			$('#reservationSearchInput').val('');
			
			// 서버에서 전체 목록 조회
			loadAllReservations();
			showNotification('검색 조건이 초기화되었습니다.', 'info');
		}

		// 예약 상세 정보 표시
		window.showReservationDetail = function(reservationId) {
			// 현재 테이블에 표시된 데이터에서 찾기
			let reservation = null;
			
			$('#reservationTableBody tr').each(function() {
				const rowReservationId = $(this).find('td:first').text();
				if (rowReservationId === reservationId) {
					const cells = $(this).find('td');
					reservation = {
						reservationId: cells.eq(0).text() === 'null' ? null : cells.eq(0).text(),
						userName: cells.eq(1).text() === 'null' ? null : cells.eq(1).text(),
						userEmail: cells.eq(2).text() === 'null' ? null : cells.eq(2).text(),
						userPhone: cells.eq(3).text() === 'null' ? null : cells.eq(3).text(),
						departure: cells.eq(4).text() === 'null' ? null : cells.eq(4).text(),
						arrival: cells.eq(5).text() === 'null' ? null : cells.eq(5).text(),
						departureTime: cells.eq(6).text() === 'null' ? null : (cells.eq(6).text() ? cells.eq(6).text() + ' 00:00' : null),
						reservationDate: cells.eq(7).text() === 'null' ? null : (cells.eq(7).text() ? cells.eq(7).text() + ' 00:00' : null),
						status: $(cells.eq(8).find('span')).text() ? $(cells.eq(8).find('span')).text().toLowerCase() : null,
						seatClass: cells.eq(9).text() === 'null' ? null : cells.eq(9).text(),
						passengerCount: cells.eq(10).text() === 'null' ? null : parseInt(cells.eq(10).text()),
						totalAmount: cells.eq(11).text() === 'null' ? null : cells.eq(11).text(),
						userBirth: null,
						flightNumber: null,
						arrivalTime: cells.eq(6).text() === 'null' ? null : (cells.eq(6).text() ? cells.eq(6).text() + ' 23:59' : null)
					};
					return false; // break
				}
			});
			
			if (!reservation) {
				showNotification('예약 정보를 찾을 수 없습니다.', 'error');
				return;
			}

			// 모달에 데이터 채우기 - null 값은 그대로 표시
			$('#modalReservationId').text(reservation.reservationId || null);
			$('#modalReservationDate').text(reservation.reservationDate || null);
			$('#modalReservationStatus').html(getStatusLabel(reservation.status) || null);
			
			const totalAmount = reservation.totalAmount != null 
				? (typeof reservation.totalAmount === 'string' 
					? parseFloat(reservation.totalAmount.replace(/[^\d]/g, '')) 
					: reservation.totalAmount)
				: null;
			$('#modalTotalAmount').text(totalAmount != null ? formatCurrency(totalAmount) : null);
			
			$('#modalUserName').text(reservation.userName || null);
			$('#modalUserEmail').text(reservation.userEmail || null);
			$('#modalUserPhone').text(reservation.userPhone || null);
			$('#modalUserBirth').text(reservation.userBirth || null);
			
			$('#modalFlightNumber').text(reservation.flightNumber || null);
			$('#modalDeparture').text(reservation.departure || null);
			$('#modalArrival').text(reservation.arrival || null);
			$('#modalDepartureTime').text(reservation.departureTime || null);
			$('#modalArrivalTime').text(reservation.arrivalTime || null);
			$('#modalSeatClass').text(reservation.seatClass || null);
			$('#modalPassengerCount').text(reservation.passengerCount != null ? reservation.passengerCount + '명' : null);

			// 삭제 버튼에 예약 ID 저장
			$('#deleteReservationBtn').data('reservation-id', reservationId);
			$('#editReservationBtn').data('reservation-id', reservationId);

			$('#reservationDetailModal').modal('show');
		};

		// 예약 삭제 확인 모달 표시 (단순화)
		function showDeleteConfirm(reservationId) {
			$('#deleteReservationId').text(reservationId || null);
			$('#deleteReservationUser').text('선택된 예약');
			$('#confirmDeleteBtn').data('reservation-id', reservationId);

			$('#reservationDetailModal').modal('hide');
			$('#deleteConfirmModal').modal('show');
		}

		// 예약 삭제 실행 (단순화)
		function deleteReservation(reservationId) {
			// 실제 서버 삭제 요청 구현 예정
			$('#deleteConfirmModal').modal('hide');
			showNotification('예약 삭제 기능은 추후 구현 예정입니다.', 'info');
		}

		// 알림 메시지 표시
		function showNotification(message, type) {
			const alertClass = {
				'success': 'alert-success',
				'warning': 'alert-warning',
				'error': 'alert-danger',
				'info': 'alert-info'
			}[type] || 'alert-info';

			const notification = $(`
				<div class="alert ${alertClass} alert-dismissible" role="alert" style="position: fixed; top: 80px; right: 20px; z-index: 9999; min-width: 300px;">
					<button type="button" class="close" data-dismiss="alert">
						<span>&times;</span>
					</button>
					${message}
				</div>
			`);

			$('body').append(notification);

			// 3초 후 자동 제거
			setTimeout(() => {
				notification.fadeOut(() => notification.remove());
			}, 3000);
		}

		// 이벤트 리스너 등록
		$('#searchReservationBtn').click(function() {
			searchReservations();
		});
		$('#clearSearchBtn').click(clearSearch);
		$('#loadAllReservationsBtn').click(loadAllReservations);

		// Enter 키로 검색
		$('#reservationSearchInput').keypress(function(e) {
			if (e.which === 13) {
				searchReservations();
			}
		});

		// 모달 버튼 이벤트
		$('#deleteReservationBtn').click(function() {
			const reservationId = $(this).data('reservation-id');
			showDeleteConfirm(reservationId);
		});

		$('#confirmDeleteBtn').click(function() {
			const reservationId = $(this).data('reservation-id');
			deleteReservation(reservationId);
		});

		$('#editReservationBtn').click(function() {
			const reservationId = $(this).data('reservation-id');
			showNotification('예약 수정 기능은 추후 구현 예정입니다.', 'info');
		});

		// 페이지 로드 시 초기화
		initializeReservationList();
	});
	</script>


</body>
</html> 