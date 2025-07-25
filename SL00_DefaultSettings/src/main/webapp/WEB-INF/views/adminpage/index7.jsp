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
										<div class="col-md-3">
											<div class="form-group">
												<label>검색 유형</label>
												<select class="form-control" id="searchType">
													<option value="reservationId">예약번호</option>
													<option value="userName">사용자명</option>
													<option value="userEmail">이메일</option>
													<option value="phone">연락처</option>
												</select>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label>검색어</label>
												<div class="input-group">
													<input type="text" class="form-control" id="reservationSearchInput" 
														   placeholder="검색어를 입력하세요..." />
													<span class="input-group-btn">
														<button class="btn btn-primary" type="button" id="searchReservationBtn">
															<i class="fa fa-search"></i> 검색
														</button>
													</span>
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label>예약 상태</label>
												<select class="form-control" id="reservationStatus">
													<option value="">전체</option>
													<option value="confirmed">확정</option>
													<option value="pending">대기</option>
													<option value="cancelled">취소</option>
													<option value="completed">완료</option>
												</select>
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
									
									<!-- 페이지네이션 -->
									<nav aria-label="검색 결과 페이지">
										<ul class="pagination justify-content-center" id="searchPagination">
											<!-- 페이지 번호들이 여기에 동적으로 추가됩니다 -->
										</ul>
									</nav>
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
		// 모의 예약 데이터 (실제 프로젝트에서는 서버에서 가져올 데이터)
		let mockReservations = [
			{
				reservationId: "KE240101001",
				userName: "김철수",
				userEmail: "kim@example.com",
				userPhone: "010-1234-5678",
				userBirth: "1985-03-15",
				flightNumber: "KE001",
				departure: "인천(ICN)",
				arrival: "도쿄(NRT)",
				departureTime: "2024-03-15 09:00",
				arrivalTime: "2024-03-15 11:30",
				reservationDate: "2024-02-01 14:30",
				status: "confirmed",
				seatClass: "이코노미",
				passengerCount: 2,
				totalAmount: 850000
			},
			{
				reservationId: "KE240101002",
				userName: "이영희",
				userEmail: "lee@example.com",
				userPhone: "010-2345-6789",
				userBirth: "1990-07-22",
				flightNumber: "KE123",
				departure: "인천(ICN)",
				arrival: "파리(CDG)",
				departureTime: "2024-03-20 13:45",
				arrivalTime: "2024-03-21 07:15",
				reservationDate: "2024-02-02 10:15",
				status: "pending",
				seatClass: "비즈니스",
				passengerCount: 1,
				totalAmount: 2500000
			},
			{
				reservationId: "KE240101003",
				userName: "박민수",
				userEmail: "park@example.com",
				userPhone: "010-3456-7890",
				userBirth: "1988-12-03",
				flightNumber: "KE456",
				departure: "부산(PUS)",
				arrival: "방콕(BKK)",
				departureTime: "2024-03-18 16:20",
				arrivalTime: "2024-03-18 19:45",
				reservationDate: "2024-02-03 16:45",
				status: "cancelled",
				seatClass: "이코노미",
				passengerCount: 3,
				totalAmount: 1200000
			},
			{
				reservationId: "KE240101004",
				userName: "정수연",
				userEmail: "jung@example.com",
				userPhone: "010-4567-8901",
				userBirth: "1992-05-18",
				flightNumber: "KE789",
				departure: "인천(ICN)",
				arrival: "뉴욕(JFK)",
				departureTime: "2024-03-25 11:00",
				arrivalTime: "2024-03-25 14:30",
				reservationDate: "2024-02-04 09:20",
				status: "completed",
				seatClass: "퍼스트",
				passengerCount: 2,
				totalAmount: 4800000
			},
			{
				reservationId: "KE240101005",
				userName: "최동훈",
				userEmail: "choi@example.com",
				userPhone: "010-5678-9012",
				userBirth: "1987-09-11",
				flightNumber: "KE321",
				departure: "제주(CJU)",
				arrival: "오사카(KIX)",
				departureTime: "2024-03-22 08:30",
				arrivalTime: "2024-03-22 10:15",
				reservationDate: "2024-02-05 13:10",
				status: "confirmed",
				seatClass: "이코노미",
				passengerCount: 1,
				totalAmount: 320000
			}
		];

		let currentReservations = [...mockReservations];
		let currentPage = 1;
		const itemsPerPage = 10;

		// 페이지 로드 시 초기 예약 목록 출력
		function initializeReservationList() {
			// 서버에서 전체 목록 조회
			loadAllReservations();
		}

		// 예약 목록 출력 함수 (로컬 데이터용)
		function displayReservations(reservations) {
			const tbody = $('#reservationTableBody');
			tbody.empty();

			if (reservations.length === 0) {
				tbody.append(`
					<tr>
						<td colspan="13" class="text-center">
							<i class="fa fa-info-circle"></i> 검색 결과가 없습니다.
						</td>
					</tr>
				`);
				return;
			}

			// 페이지네이션 계산
			const startIndex = (currentPage - 1) * itemsPerPage;
			const endIndex = startIndex + itemsPerPage;
			const pageReservations = reservations.slice(startIndex, endIndex);

			pageReservations.forEach(reservation => {
				const statusLabel = getStatusLabel(reservation.status);
				const formattedAmount = formatCurrency(reservation.totalAmount);
				
				tbody.append(`
					<tr>
						<td style="vertical-align: middle;">${reservation.reservationId}</td>
						<td style="vertical-align: middle;">${reservation.userName}</td>
						<td style="vertical-align: middle;">${reservation.userEmail}</td>
						<td style="vertical-align: middle;">${reservation.userPhone}</td>
						<td style="vertical-align: middle;">${reservation.departure}</td>
						<td style="vertical-align: middle;">${reservation.arrival}</td>
						<td style="vertical-align: middle;">${reservation.departureTime.split(' ')[0]}</td>
						<td style="vertical-align: middle;">${reservation.reservationDate.split(' ')[0]}</td>
						<td style="vertical-align: middle;">${statusLabel}</td>
						<td style="vertical-align: middle;">${reservation.seatClass}</td>
						<td style="vertical-align: middle;">${reservation.passengerCount}명</td>
						<td style="vertical-align: middle;">${formattedAmount}</td>
						<td style="vertical-align: middle;">
							<button class="btn btn-info btn-xs" onclick="showReservationDetail('${reservation.reservationId}')">
								<i class="fa fa-eye"></i> 상세
							</button>
						</td>
					</tr>
				`);
			});

			// 페이지네이션 업데이트
			updatePagination(reservations.length);
		}

		// 서버 데이터 출력 함수 (서버에서 이미 페이지네이션 처리됨)
		function displayServerReservations(reservations) {
			const tbody = $('#reservationTableBody');
			tbody.empty();

			if (reservations.length === 0) {
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
				const statusLabel = getStatusLabel(reservation.status);
				const formattedAmount = formatCurrency(reservation.totalAmount);
				
				tbody.append(`
					<tr>
						<td style="vertical-align: middle;">${reservation.reservationId}</td>
						<td style="vertical-align: middle;">${reservation.userName}</td>
						<td style="vertical-align: middle;">${reservation.userEmail}</td>
						<td style="vertical-align: middle;">${reservation.userPhone}</td>
						<td style="vertical-align: middle;">${reservation.departure}</td>
						<td style="vertical-align: middle;">${reservation.arrival}</td>
						<td style="vertical-align: middle;">${reservation.departureTime.split(' ')[0]}</td>
						<td style="vertical-align: middle;">${reservation.reservationDate.split(' ')[0]}</td>
						<td style="vertical-align: middle;">${statusLabel}</td>
						<td style="vertical-align: middle;">${reservation.seatClass}</td>
						<td style="vertical-align: middle;">${reservation.passengerCount}명</td>
						<td style="vertical-align: middle;">${formattedAmount}</td>
						<td style="vertical-align: middle;">
							<button class="btn btn-info btn-xs" onclick="showReservationDetail('${reservation.reservationId}')">
								<i class="fa fa-eye"></i> 상세
							</button>
						</td>
					</tr>
				`);
			});
		}

		// 상태에 따른 라벨 생성
		function getStatusLabel(status) {
			const statusMap = {
				'confirmed': '<span class="label label-success">확정</span>',
				'pending': '<span class="label label-warning">대기</span>',
				'cancelled': '<span class="label label-danger">취소</span>',
				'completed': '<span class="label label-info">완료</span>'
			};
			return statusMap[status] || '<span class="label label-default">알 수 없음</span>';
		}

		// 원화 포맷팅 함수
		function formatCurrency(amount) {
			return new Intl.NumberFormat('ko-KR', {
				style: 'currency',
				currency: 'KRW'
			}).format(amount);
		}

		// 결과 수 업데이트
		function updateResultCount(count) {
			$('#resultCount').text(count);
		}

		// 페이지네이션 업데이트 (로컬 데이터용)
		function updatePagination(totalItems) {
			const totalPages = Math.ceil(totalItems / itemsPerPage);
			const pagination = $('#searchPagination');
			pagination.empty();

			if (totalPages <= 1) return;

			// 이전 버튼
			if (currentPage > 1) {
				pagination.append(`
					<li>
						<a href="#" onclick="changePage(${currentPage - 1})">
							<i class="fa fa-chevron-left"></i>
						</a>
					</li>
				`);
			}

			// 페이지 번호들
			for (let i = 1; i <= totalPages; i++) {
				const activeClass = i === currentPage ? 'active' : '';
				pagination.append(`
					<li class="${activeClass}">
						<a href="#" onclick="changePage(${i})">${i}</a>
					</li>
				`);
			}

			// 다음 버튼
			if (currentPage < totalPages) {
				pagination.append(`
					<li>
						<a href="#" onclick="changePage(${currentPage + 1})">
							<i class="fa fa-chevron-right"></i>
						</a>
					</li>
				`);
			}
		}

		// 페이지네이션 업데이트 (서버 데이터용)
		function updatePaginationWithTotal(totalCount) {
			const totalPages = Math.ceil(totalCount / itemsPerPage);
			const pagination = $('#searchPagination');
			pagination.empty();

			if (totalPages <= 1) return;

			// 이전 버튼
			if (currentPage > 1) {
				pagination.append(`
					<li>
						<a href="#" onclick="changePageAndSearch(${currentPage - 1})">
							<i class="fa fa-chevron-left"></i>
						</a>
					</li>
				`);
			}

			// 페이지 번호들 (최대 10개 페이지만 표시)
			const startPage = Math.max(1, currentPage - 5);
			const endPage = Math.min(totalPages, startPage + 9);

			for (let i = startPage; i <= endPage; i++) {
				const activeClass = i === currentPage ? 'active' : '';
				pagination.append(`
					<li class="${activeClass}">
						<a href="#" onclick="changePageAndSearch(${i})">${i}</a>
					</li>
				`);
			}

			// 다음 버튼
			if (currentPage < totalPages) {
				pagination.append(`
					<li>
						<a href="#" onclick="changePageAndSearch(${currentPage + 1})">
							<i class="fa fa-chevron-right"></i>
						</a>
					</li>
				`);
			}
		}

		// 페이지 변경 (로컬 데이터용)
		window.changePage = function(page) {
			currentPage = page;
			displayReservations(currentReservations);
		};

		// 페이지 변경 후 검색 재실행 (서버 데이터용)
		window.changePageAndSearch = function(page) {
			currentPage = page;
			
			// 현재 검색 조건이 있으면 검색 재실행, 없으면 전체 목록 조회
			const searchInput = $('#reservationSearchInput').val().trim();
			const statusFilter = $('#reservationStatus').val();
			
			if (searchInput || statusFilter) {
				searchReservations();
			} else {
				loadAllReservations();
			}
		};

		// 예약 검색 함수
		function searchReservations() {
			const searchType = $('#searchType').val();
			const searchInput = $('#reservationSearchInput').val().trim();
			const statusFilter = $('#reservationStatus').val();

			// 검색 버튼 비활성화
			$('#searchReservationBtn').prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> 검색중...');

			// 요청 파라미터 구성
			const params = new URLSearchParams();
			params.append('searchType', searchType);
			if (searchInput) {
				params.append('searchKeyword', searchInput);
			}
			if (statusFilter) {
				params.append('reservationStatus', statusFilter);
			}
			params.append('page', currentPage);
			params.append('size', itemsPerPage);

					// 서버에 검색 요청
		fetch('${pageContext.request.contextPath}/flight/reservationsearch?' + params.toString(), {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			}
		})
		.then(response => {
			if (!response.ok) {
				throw new Error('서버 응답 오류: ' + response.status);
			}
			return response.json();
		})
		.then(data => {
			console.log('서버 응답:', data); // 디버깅용
			if (data.success) {
				// 서버에서 받은 데이터로 업데이트
				const reservations = data.reservations || [];
				const totalCount = data.totalCount || 0;
				
				// 서버에서 받은 데이터를 직접 테이블에 표시 (페이지네이션은 서버에서 처리됨)
				displayServerReservations(reservations);
				updateResultCount(totalCount);
				updatePaginationWithTotal(totalCount);

				// 검색 결과 메시지
				if (reservations.length === 0) {
					showNotification('검색 결과가 없습니다.', 'warning');
				} else {
					showNotification(`${totalCount}건의 검색 결과를 찾았습니다.`, 'success');
				}
			} else {
				throw new Error(data.message || '검색 중 오류가 발생했습니다.');
			}
		})
		.catch(error => {
			console.error('검색 오류:', error);
			showNotification('서버 연동 오류가 발생했습니다. 목 데이터로 대체합니다.', 'warning');
			
			// 오류 발생 시 목 데이터로 폴백
			searchReservationsWithMockData();
		})
			.finally(() => {
				// 검색 버튼 재활성화
				$('#searchReservationBtn').prop('disabled', false).html('<i class="fa fa-search"></i> 검색');
			});
		}

		// 목 데이터로 검색 (서버 연동 전 테스트용)
		function searchReservationsWithMockData() {
			const searchType = $('#searchType').val();
			const searchInput = $('#reservationSearchInput').val().trim().toLowerCase();
			const statusFilter = $('#reservationStatus').val();

			let filteredReservations = mockReservations.filter(reservation => {
				// 텍스트 검색 조건
				let textMatch = true;
				if (searchInput) {
					switch (searchType) {
						case 'reservationId':
							textMatch = reservation.reservationId.toLowerCase().includes(searchInput);
							break;
						case 'userName':
							textMatch = reservation.userName.toLowerCase().includes(searchInput);
							break;
						case 'userEmail':
							textMatch = reservation.userEmail.toLowerCase().includes(searchInput);
							break;
						case 'phone':
							textMatch = reservation.userPhone.includes(searchInput);
							break;
					}
				}

				// 상태 필터 조건
				let statusMatch = true;
				if (statusFilter) {
					statusMatch = reservation.status === statusFilter;
				}

				return textMatch && statusMatch;
			});

			currentReservations = filteredReservations;
			displayReservations(currentReservations);
			updateResultCount(currentReservations.length);
			updatePagination(currentReservations.length);
		}

		// 전체 예약 목록 조회
		function loadAllReservations() {
			// 검색 조건 초기화
			$('#searchType').val('reservationId');
			$('#reservationSearchInput').val('');
			$('#reservationStatus').val('');
			currentPage = 1;

			// 전체 조회 버튼 비활성화
			$('#loadAllReservationsBtn').prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> 조회중...');

					// 서버에 전체 목록 요청 (빈 검색 조건으로 전체 조회)
		const params = new URLSearchParams();
		params.append('searchType', 'reservationId');
		params.append('page', currentPage);
		params.append('size', itemsPerPage);

		fetch('${pageContext.request.contextPath}/flight/reservationsearch?' + params.toString(), {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			}
		})
		.then(response => {
			if (!response.ok) {
				throw new Error('서버 응답 오류: ' + response.status);
			}
			return response.json();
		})
		.then(data => {
			console.log('전체 목록 서버 응답:', data); // 디버깅용
			if (data.success) {
				const reservations = data.reservations || [];
				const totalCount = data.totalCount || 0;
				
				displayServerReservations(reservations);
				updateResultCount(totalCount);
				updatePaginationWithTotal(totalCount);
				showNotification('전체 예약 목록을 조회했습니다.', 'info');
			} else {
				throw new Error(data.message || '목록 조회 중 오류가 발생했습니다.');
			}
		})
		.catch(error => {
			console.error('목록 조회 오류:', error);
			showNotification('서버 연동 오류가 발생했습니다. 목 데이터로 대체합니다.', 'warning');
			
			// 오류 발생 시 목 데이터로 폴백
			loadAllReservationsWithMockData();
		})
			.finally(() => {
				// 버튼 재활성화
				$('#loadAllReservationsBtn').prop('disabled', false).html('<i class="fa fa-list"></i> 전체 예약 조회');
			});
		}

		// 목 데이터로 전체 조회 (서버 연동 전 테스트용)
		function loadAllReservationsWithMockData() {
			currentReservations = [...mockReservations];
			currentPage = 1;
			displayReservations(currentReservations);
			updateResultCount(currentReservations.length);
			updatePagination(currentReservations.length);
		}

		// 검색 조건 초기화
		function clearSearch() {
			$('#searchType').val('reservationId');
			$('#reservationSearchInput').val('');
			$('#reservationStatus').val('');
			currentPage = 1;
			
			// 서버에서 전체 목록 조회
			loadAllReservations();
			showNotification('검색 조건이 초기화되었습니다.', 'info');
		}

		// 예약 상세 정보 표시
		window.showReservationDetail = function(reservationId) {
			// 먼저 현재 화면에 표시된 데이터에서 찾기
			let reservation = null;
			
			// 현재 테이블에 표시된 데이터에서 찾기
			$('#reservationTableBody tr').each(function() {
				const rowReservationId = $(this).find('td:first').text();
				if (rowReservationId === reservationId) {
					const cells = $(this).find('td');
					reservation = {
						reservationId: cells.eq(0).text(),
						userName: cells.eq(1).text(),
						userEmail: cells.eq(2).text(),
						userPhone: cells.eq(3).text(),
						departure: cells.eq(4).text(),
						arrival: cells.eq(5).text(),
						departureTime: cells.eq(6).text() + ' 00:00', // 시간 정보 추가
						reservationDate: cells.eq(7).text() + ' 00:00', // 시간 정보 추가
						status: $(cells.eq(8).find('span')).text().toLowerCase(),
						seatClass: cells.eq(9).text(),
						passengerCount: parseInt(cells.eq(10).text()),
						totalAmount: cells.eq(11).text(),
						// 기본값들
						userBirth: '1990-01-01',
						flightNumber: 'KE000',
						arrivalTime: cells.eq(6).text() + ' 23:59'
					};
					return false; // break
				}
			});
			
			// 테이블에서 찾지 못했으면 목 데이터에서 찾기
			if (!reservation) {
				reservation = mockReservations.find(r => r.reservationId === reservationId);
			}
			
			if (!reservation) {
				showNotification('예약 정보를 찾을 수 없습니다.', 'error');
				return;
			}

			// 모달에 데이터 채우기
			$('#modalReservationId').text(reservation.reservationId);
			$('#modalReservationDate').text(reservation.reservationDate);
			$('#modalReservationStatus').html(getStatusLabel(reservation.status));
			// 금액이 문자열일 경우 숫자로 변환 후 포맷팅
			const totalAmount = typeof reservation.totalAmount === 'string' 
				? parseFloat(reservation.totalAmount.replace(/[^\d]/g, '')) 
				: reservation.totalAmount;
			$('#modalTotalAmount').text(formatCurrency(totalAmount));
			
			$('#modalUserName').text(reservation.userName);
			$('#modalUserEmail').text(reservation.userEmail);
			$('#modalUserPhone').text(reservation.userPhone);
			$('#modalUserBirth').text(reservation.userBirth);
			
			$('#modalFlightNumber').text(reservation.flightNumber);
			$('#modalDeparture').text(reservation.departure);
			$('#modalArrival').text(reservation.arrival);
			$('#modalDepartureTime').text(reservation.departureTime);
			$('#modalArrivalTime').text(reservation.arrivalTime);
			$('#modalSeatClass').text(reservation.seatClass);
			$('#modalPassengerCount').text(reservation.passengerCount + '명');

			// 삭제 버튼에 예약 ID 저장
			$('#deleteReservationBtn').data('reservation-id', reservationId);
			$('#editReservationBtn').data('reservation-id', reservationId);

			$('#reservationDetailModal').modal('show');
		};

		// 예약 삭제 확인 모달 표시
		function showDeleteConfirm(reservationId) {
			const reservation = mockReservations.find(r => r.reservationId === reservationId);
			if (!reservation) return;

			$('#deleteReservationId').text(reservation.reservationId);
			$('#deleteReservationUser').text(reservation.userName);
			$('#confirmDeleteBtn').data('reservation-id', reservationId);

			$('#reservationDetailModal').modal('hide');
			$('#deleteConfirmModal').modal('show');
		}

		// 예약 삭제 실행
		function deleteReservation(reservationId) {
			const index = mockReservations.findIndex(r => r.reservationId === reservationId);
			if (index !== -1) {
				mockReservations.splice(index, 1);
				
				// 현재 검색 결과에서도 제거
				const currentIndex = currentReservations.findIndex(r => r.reservationId === reservationId);
				if (currentIndex !== -1) {
					currentReservations.splice(currentIndex, 1);
				}

				displayReservations(currentReservations);
				updateResultCount(currentReservations.length);
				
				$('#deleteConfirmModal').modal('hide');
				showNotification('예약이 성공적으로 삭제되었습니다.', 'success');
			}
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
			currentPage = 1; // 새 검색 시 첫 페이지로 이동
			searchReservations();
		});
		$('#clearSearchBtn').click(clearSearch);
		$('#loadAllReservationsBtn').click(loadAllReservations);

		// Enter 키로 검색
		$('#reservationSearchInput').keypress(function(e) {
			if (e.which === 13) {
				currentPage = 1; // 새 검색 시 첫 페이지로 이동
				searchReservations();
			}
		});

		// 상태 필터 변경 시 자동 검색
		$('#reservationStatus').change(function() {
			if ($('#reservationSearchInput').val().trim() || $(this).val()) {
				currentPage = 1; // 필터 변경 시 첫 페이지로 이동
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