<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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

<title>Gentelella Alela! | 비행 스케줄 현황</title>

<link
	href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/static/vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/static/vendors/nprogress/nprogress.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet">

<link href="${pageContext.request.contextPath}/resources/static/build/css/custom.min.css"
	rel="stylesheet">

<style>
.flight-schedule-container {
	display: flex;
	flex-direction: column;
}

.query-controls-container {
	display: flex;
	flex-wrap: wrap;
	align-items: flex-end;
	margin-bottom: 20px;
	gap: 15px;
}

.calendar-input-container {
	flex: 0 0 230px;
}

#flightFilterContainer .btn {
	margin-right: 5px;
	margin-bottom: 5px;
}

.schedule-table-container {
	flex: 1;
	min-width: 0;
}

#dateSelectorInput {
	cursor: pointer;
	background-color: #fff !important;
}

.table-responsive {
	overflow-x: auto;
}

.pagination>.active>a, .pagination>.active>a:focus, .pagination>.active>a:hover,
	.pagination>.active>span, .pagination>.active>span:focus, .pagination>.active>span:hover
	{
	background-color: #1ABB9C;
	border-color: #1ABB9C;
}

.loading-overlay {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(255, 255, 255, 0.7);
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 1000;
}

.loading-overlay .fa {
	font-size: 2em;
	color: #1ABB9C;
}
</style>
</head>

<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				{/* --- 사이드바 전체 --- */}
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="index.html" class="site_title"><i class="fa fa-paw"></i>
							<span>Gentelella Alela!</span></a>
					</div>
					<div class="clearfix"></div>
					<div class="profile clearfix">
						<div class="profile_pic">
							<img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="..."
								class="img-circle profile_img">
						</div>
						<div class="profile_info">
							<span>Welcome,</span>
							<h2>John Doe</h2>
						</div>
					</div>
					<br />
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
						<div class="menu_section">
							<h3>General</h3>
							<ul class="nav side-menu">
								<li><a><i class="fa fa-home"></i> Home <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="index.html">Dashboard</a></li>
										<li><a href="index2.html">Dashboard2</a></li>
										<li><a href="index3.html">Dashboard3</a></li>
										<li><a href="index4.html">좌석관리</a></li>
										<li><a href="index5.html">비행스케줄표</a></li>
									</ul></li>
								<li><a><i class="fa fa-edit"></i> Forms <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="form.html">General Form</a></li>
										<li><a href="form_advanced.html">Advanced Components</a></li>
										<li><a href="form_validation.html">Form Validation</a></li>
										<li><a href="form_wizards.html">Form Wizard</a></li>
										<li><a href="form_upload.html">Form Upload</a></li>
										<li><a href="form_buttons.html">Form Buttons</a></li>
									</ul></li>
								<li><a><i class="fa fa-desktop"></i> UI Elements <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="general_elements.html">General Elements</a></li>
										<li><a href="media_gallery.html">Media Gallery</a></li>
										<li><a href="typography.html">Typography</a></li>
										<li><a href="icons.html">Icons</a></li>
										<li><a href="glyphicons.html">Glyphicons</a></li>
										<li><a href="widgets.html">Widgets</a></li>
										<li><a href="invoice.html">Invoice</a></li>
										<li><a href="inbox.html">Inbox</a></li>
										<li><a href="calendar.html">Calendar</a></li>
									</ul></li>
								<li><a><i class="fa fa-table"></i> Tables <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="tables.html">Tables</a></li>
										<li><a href="tables_dynamic.html">Table Dynamic</a></li>
									</ul></li>
								<li><a><i class="fa fa-bar-chart-o"></i> Data
										Presentation <span class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="chartjs.html">Chart JS</a></li>
										<li><a href="chartjs2.html">Chart JS2</a></li>
										<li><a href="morisjs.html">Moris JS</a></li>
										<li><a href="echarts.html">ECharts</a></li>
										<li><a href="other_charts.html">Other Charts</a></li>
									</ul></li>
								<li><a><i class="fa fa-clone"></i>Layouts <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="fixed_sidebar.html">Fixed Sidebar</a></li>
										<li><a href="fixed_footer.html">Fixed Footer</a></li>
									</ul></li>
							</ul>
						</div>
						<div class="menu_section">
							<h3>Live On</h3>
							<ul class="nav side-menu">
								<li><a><i class="fa fa-bug"></i> Additional Pages <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="e_commerce.html">E-commerce</a></li>
										<li><a href="projects.html">Projects</a></li>
										<li><a href="project_detail.html">Project Detail</a></li>
										<li><a href="contacts.html">Contacts</a></li>
										<li><a href="profile.html">Profile</a></li>
									</ul></li>
								<li><a><i class="fa fa-windows"></i> Extras <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="page_403.html">403 Error</a></li>
										<li><a href="page_404.html">404 Error</a></li>
										<li><a href="page_500.html">500 Error</a></li>
										<li><a href="plain_page.html">Plain Page</a></li>
										<li><a href="login.html">Login Page</a></li>
										<li><a href="pricing_tables.html">Pricing Tables</a></li>
									</ul></li>
								<li><a><i class="fa fa-sitemap"></i> Multilevel Menu <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="#level1_1">Level One</a>
										<li><a>Level One<span class="fa fa-chevron-down"></span></a>
											<ul class="nav child_menu">
												<li class="sub_menu"><a href="level2.html">Level
														Two</a></li>
												<li><a href="#level2_1">Level Two</a></li>
												<li><a href="#level2_2">Level Two</a></li>
											</ul></li>
										<li><a href="#level1_2">Level One</a></li>
									</ul></li>
								<li><a href="javascript:void(0)"><i
										class="fa fa-laptop"></i> Landing Page <span
										class="label label-success pull-right">Coming Soon</span></a></li>
							</ul>
						</div>
					</div>
					<div class="sidebar-footer hidden-small">
						<a data-toggle="tooltip" data-placement="top" title="Settings"><span
							class="glyphicon glyphicon-cog" aria-hidden="true"></span></a> <a
							data-toggle="tooltip" data-placement="top" title="FullScreen"><span
							class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span></a>
						<a data-toggle="tooltip" data-placement="top" title="Lock"><span
							class="glyphicon glyphicon-eye-close" aria-hidden="true"></span></a>
						<a data-toggle="tooltip" data-placement="top" title="Logout"
							href="login.html"><span class="glyphicon glyphicon-off"
							aria-hidden="true"></span></a>
					</div>
				</div>
			</div>

			{/* --- 상단 네비게이션 전체 --- */}
			<div class="top_nav">
				<div class="nav_menu">
					<div class="nav toggle">
						<a id="menu_toggle"><i class="fa fa-bars"></i></a>
					</div>
					<nav class="nav navbar-nav">
						<ul class=" navbar-right">
							<li class="nav-item dropdown open" style="padding-left: 15px;">
								<a href="javascript:;" class="user-profile dropdown-toggle"
								aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown"
								aria-expanded="false"> <img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="">John
									Doe
							</a>
								<div class="dropdown-menu dropdown-usermenu pull-right"
									aria-labelledby="navbarDropdown">
									<a class="dropdown-item" href="javascript:;"> Profile</a> <a
										class="dropdown-item" href="javascript:;"> <span
										class="badge bg-red pull-right">50%</span> <span>Settings</span>
									</a> <a class="dropdown-item" href="javascript:;">Help</a> <a
										class="dropdown-item" href="login.html"><i
										class="fa fa-sign-out pull-right"></i> Log Out</a>
								</div>
							</li>
							<li role="presentation" class="nav-item dropdown open"><a
								href="javascript:;" class="dropdown-toggle info-number"
								id="navbarDropdown1" data-toggle="dropdown"
								aria-expanded="false"> <i class="fa fa-envelope-o"></i> <span
									class="badge bg-green">6</span>
							</a>
								<ul class="dropdown-menu list-unstyled msg_list" role="menu"
									aria-labelledby="navbarDropdown1">
									<li class="nav-item"><a class="dropdown-item"> <span
											class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg"
												alt="Profile Image" /></span><span><span>John Smith</span><span
												class="time">3 mins ago</span></span><span class="message">Film
												festivals used to be do-or-die moments for movie makers.
												They were where...</span></a></li>
									<li class="nav-item"><a class="dropdown-item"> <span
											class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg"
												alt="Profile Image" /></span><span><span>John Smith</span><span
												class="time">3 mins ago</span></span><span class="message">Film
												festivals used to be do-or-die moments for movie makers.
												They were where...</span></a></li>
									<li class="nav-item"><a class="dropdown-item"> <span
											class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg"
												alt="Profile Image" /></span><span><span>John Smith</span><span
												class="time">3 mins ago</span></span><span class="message">Film
												festivals used to be do-or-die moments for movie makers.
												They were where...</span></a></li>
									<li class="nav-item"><a class="dropdown-item"> <span
											class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg"
												alt="Profile Image" /></span><span><span>John Smith</span><span
												class="time">3 mins ago</span></span><span class="message">Film
												festivals used to be do-or-die moments for movie makers.
												They were where...</span></a></li>
									<li class="nav-item">
										<div class="text-center">
											<a class="dropdown-item"> <strong>See All Alerts</strong>
												<i class="fa fa-angle-right"></i>
											</a>
										</div>
									</li>
								</ul></li>
						</ul>
					</nav>
				</div>
			</div>

			{/* --- 메인 컨텐츠 --- */}
			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>실시간 비행 스케줄 현황</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 ">
							<div class="x_panel">
								<div class="x_title">
									<h2>조회 조건</h2>
									<div class="pull-right" style="margin-right: 50px;">
										<button type="button" class="btn btn-success" id="saveToDbBtn"
											style="margin-right: 10px;">
											<i class="fa fa-database"></i> DB에 저장하기
										</button>
										<button type="button" class="btn btn-primary" id="syncDataBtn">
											<i class="fa fa-refresh"></i> 최신데이터 동기화
										</button>
									</div>
									<ul class="nav navbar-right panel_toolbox">
										<li><a class="collapse-link"><i
												class="fa fa-chevron-up"></i></a></li>
										<li><a class="close-link"><i class="fa fa-close"></i></a></li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div class="flight-schedule-container">
										<div class="query-controls-container">
											<div class="calendar-input-container">
												<label for="dateSelectorInput"
													style="display: block; margin-bottom: 5px;">날짜 선택</label>
												<div class="input-group">
													<input type="text" id="dateSelectorInput"
														class="form-control" readonly placeholder="날짜를 선택하세요">
													<span class="input-group-addon" style="cursor: pointer;">
														<span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
											</div>
											<div id="flightFilterContainer" class="btn-group"
												role="group" aria-label="Flight Type Filters">
												<label style="display: block; margin-bottom: 5px;">스케줄 조회</label>
												<div>
													<button type="button"
														class="btn btn-default filter-btn active"
														data-filter="all">전체 비행스케줄</button>
												</div>
											</div>
										</div>

										<div class="schedule-table-container">
											<h4 id="scheduleDateDisplay">오늘의 전체 비행스케줄</h4>
											<div class="table-responsive">
												<table class="table table-striped table-bordered"
													id="flightScheduleTable">
													<thead>
														<tr>
															<th>편명</th>
															<th>항공사</th>
															<th>출발지</th>
															<th>도착지</th>
															<th>출발시간</th>
															<th>도착시간</th>
															<th>상태</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td colspan="7" class="text-center">날짜와 필터를 선택해주세요.</td>
														</tr>
													</tbody>
												</table>
											</div>
											<nav aria-label="Page navigation">
												<ul class="pagination justify-content-center"
													id="paginationControls"></ul>
											</nav>
											<div id="loadingIndicator" class="loading-overlay"
												style="display: none; position: relative; min-height: 100px;">
												<i class="fa fa-spinner fa-spin"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			{/* --- 푸터 전체 --- */}
			<footer>
				<div class="pull-right">
					Gentelella - Bootstrap Admin Template by <a
						href="https://colorlib.com">Colorlib</a>
				</div>
				<div class="clearfix"></div>
			</footer>
		</div>
	</div>

	{/* --- JavaScript 라이브러리 로드 전체 --- */}
	<script src="${pageContext.request.contextPath}/resources/static/vendors/jquery/dist/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/fastclick/lib/fastclick.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/nprogress/nprogress.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/Chart.js/dist/Chart.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/static/vendors/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/raphael/raphael.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/morris.js/morris.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/gauge.js/dist/gauge.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/skycons/skycons.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/Flot/jquery.flot.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/Flot/jquery.flot.pie.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/Flot/jquery.flot.time.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/Flot/jquery.flot.stack.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/Flot/jquery.flot.resize.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/static/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/static/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/static/vendors/flot.curvedlines/curvedLines.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/DateJS/build/date.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/vendors/moment/min/moment.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

	<script src="${pageContext.request.contextPath}/resources/static/build/js/custom.js"></script>



	<script>
    // --- 1. 핸들러가 넘겨준 데이터를 JavaScript 변수로 바로 받기 (수정된 방식) ---
    // 핸들러에서 request.setAttribute("initialData", jsonString)으로 넘겨준 값입니다.
    // 이 방식이 더 간단하고 안전합니다.
    var initialData = <c:out value="${initialData}" default="[]" escapeXml="false"/>;


    $(document).ready(function() {
        const ITEMS_PER_PAGE = 50;
        let currentPage = 1;
        let currentSchedules = [];
        
        // 페이지 로딩 시의 날짜는 서버에서 데이터를 가져온 날짜(오늘)로 설정합니다.
        let selectedDate = moment().format('YYYY-MM-DD');
        let currentFilterType = 'all'; 

        const $loadingIndicator = $('#loadingIndicator');
        const $dateSelectorInput = $('#dateSelectorInput');
        const $flightScheduleTableBody = $('#flightScheduleTable tbody');
        const $paginationControls = $('#paginationControls');
        const $scheduleDateDisplay = $('#scheduleDateDisplay');
        const $filterButtons = $('.filter-btn');

        // 로딩 상태 표시 - 데이터 로드 중일 때 스피너와 로딩 메시지를 보여줌
        function showLoading() {
            $loadingIndicator.show();
            $flightScheduleTableBody.empty().append('<tr><td colspan="7" class="text-center">운항 스케줄을 불러오고 있습니다...</td></tr>');
            $paginationControls.empty();
        }

        // 로딩 상태 숨김 - 데이터 로드 완료 후 로딩 인디케이터를 숨김
        function hideLoading() {
            $loadingIndicator.hide();
        }

        // 스케줄 제목 업데이트 - 선택된 날짜에 따라 제목을 동적으로 변경
        function updateScheduleDisplayTitle(dateStr, filter) {
            let dateFormatted = moment(dateStr).format("YYYY년 MM월 DD일");
            let filterText = " 전체 비행스케줄";
            $scheduleDateDisplay.text(dateFormatted + filterText);
        }

        $dateSelectorInput.daterangepicker({
            singleDatePicker: true,
            showDropdowns: true,
            autoUpdateInput: false,
            locale: {
                format: 'YYYY-MM-DD',
                applyLabel: "선택",
                cancelLabel: "취소",
                daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
                monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
            }
        });

        $dateSelectorInput.on('apply.daterangepicker', function(ev, picker) {
            const newlySelectedDate = picker.startDate.format('YYYY-MM-DD');
            console.log('달력에서 선택된 날짜:', newlySelectedDate);
            selectedDate = newlySelectedDate; 
            $(this).val(selectedDate);  

            console.log('달력 선택 - 날짜:', selectedDate);
            loadAndDisplaySchedules(selectedDate, 'all');
        });
        
        $('.input-group-addon').on('click', function() {
            $dateSelectorInput.trigger('click'); 
        });

        $filterButtons.on('click', function() {
            const $this = $(this);
            $filterButtons.removeClass('active btn-success btn-info').addClass('btn-default');
            $this.removeClass('btn-default').addClass('active btn-success');

            // 현재 선택된 날짜로 전체 비행스케줄 조회
            const dateToUse = $dateSelectorInput.val() || selectedDate;
            console.log('필터 버튼 클릭 - 사용할 날짜:', dateToUse);
            
            loadAndDisplaySchedules(dateToUse, 'all'); 
        });

        // 스케줄 데이터 로드 및 표시 - 서버에서 스케줄 데이터를 가져와서 테이블에 표시
        function loadAndDisplaySchedules(dateToQuery, filterToApply) {
            console.log('loadAndDisplaySchedules 호출됨 - 날짜:', dateToQuery, '필터:', filterToApply);
            
            // 전역 변수 업데이트
            selectedDate = dateToQuery;
            currentFilterType = filterToApply;
            
            // 달력 입력 필드도 업데이트
            $dateSelectorInput.val(selectedDate);
            
            showLoading();
            updateScheduleDisplayTitle(dateToQuery, filterToApply);

            // 스케줄 데이터를 API를 통해 가져오기
            $.ajax({
                url: '${pageContext.request.contextPath}/api/flights',
                type: 'GET',
                data: { 
                    date: dateToQuery,
                    flightType: filterToApply 
                },
                dataType: 'json',
                cache: false,
                headers: {
                    'Accept': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                },
                success: function(data) {
                    currentSchedules = [];
                    if (Array.isArray(data) && data.length > 0 && data[0] && data[0].error) {
                        $flightScheduleTableBody.empty().append('<tr><td colspan="7" class="text-center">스케줄 조회 실패: ' + data[0].error + '</td></tr>');
                    } else if (Array.isArray(data)) {
                        currentSchedules = data;
                    } else {
                        $flightScheduleTableBody.empty().append('<tr><td colspan="7" class="text-center">잘못된 형식의 응답입니다.</td></tr>');
                    }
                    
                    currentPage = 1; 
                    renderTable();
                    renderPagination();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('AJAX 오류:', textStatus, errorThrown);
                    console.error('응답 상태:', jqXHR.status);
                    console.error('응답 텍스트:', jqXHR.responseText);
                    $flightScheduleTableBody.empty().append('<tr><td colspan="7" class="text-center">스케줄을 불러오는 중 오류가 발생했습니다. (서버 응답 코드: ' + jqXHR.status + ')</td></tr>');
                    currentSchedules = [];
                    renderTable();
                    renderPagination();
                },
                complete: function() {
                    hideLoading();
                }
            });
        }

        // 테이블 렌더링 - 현재 페이지에 해당하는 스케줄 데이터를 테이블에 표시
        function renderTable() {
            if (isNaN(parseInt(currentPage, 10))) currentPage = 1;
            $flightScheduleTableBody.empty();

            if (!Array.isArray(currentSchedules) || currentSchedules.length === 0) {
                 $flightScheduleTableBody.append('<tr><td colspan="7" class="text-center">선택하신 조건에 해당하는 스케줄이 없습니다.</td></tr>');
                return;
            }
            
            if (currentSchedules[0] && currentSchedules[0].error) {
                 $flightScheduleTableBody.append('<tr><td colspan="7" class="text-center">' + currentSchedules[0].error + '</td></tr>');
                return;
            }

            const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
            const endIndex = startIndex + ITEMS_PER_PAGE;
            const pageSchedules = currentSchedules.slice(startIndex, endIndex);
            
            if (pageSchedules.length === 0 && currentSchedules.length > 0) {
                 $flightScheduleTableBody.append('<tr><td colspan="7" class="text-center">해당 페이지에 스케줄이 없습니다.</td></tr>');
                 return;
            }
            
            let rowsHtml = "";
            pageSchedules.forEach(function(flight) {
                rowsHtml += '<tr>' +
                                '<td>' + (flight.flightNo || 'N/A') + '</td>' +
                                '<td>' + (flight.airline || 'N/A') + '</td>' +
                                '<td>' + (flight.origin || 'N/A') + '</td>' +
                                '<td>' + (flight.destination || 'N/A') + '</td>' +
                                '<td>' + (flight.departureTime || 'N/A') + '</td>' +
                                '<td>' + (flight.arrivalTime || 'N/A') + '</td>' +
                                '<td>' + (flight.status || 'N/A') + '</td>' +
                              '</tr>';
            });
            $flightScheduleTableBody.html(rowsHtml);
        }

        // 페이지네이션 렌더링 - 전체 데이터 수에 따라 페이지 번호 버튼들을 생성
        function renderPagination() {
            if (isNaN(parseInt(currentPage, 10))) currentPage = 1;
            $paginationControls.empty(); 
            const totalPages = Math.ceil(currentSchedules.length / ITEMS_PER_PAGE); 
            if (totalPages <= 1) { return; }

            if (currentPage > 1) { $paginationControls.append('<li class="page-item"><a class="page-link" href="#" data-page="' + (currentPage - 1) + '">&laquo;</a></li>'); } 
            else { $paginationControls.append('<li class="page-item disabled"><span class="page-link">&laquo;</span></li>'); }
            let startPage = Math.max(1, currentPage - 2);
            let endPage = Math.min(totalPages, currentPage + 2);
            if (endPage - startPage + 1 < 5) { 
                if (startPage === 1) endPage = Math.min(totalPages, startPage + 4);
                else if (endPage === totalPages) startPage = Math.max(1, endPage - 4);
            }
            if (startPage > 1) { 
                $paginationControls.append('<li class="page-item"><a class="page-link" href="#" data-page="1">1</a></li>');
                if (startPage > 2) $paginationControls.append('<li class="page-item disabled"><span class="page-link">...</span></li>');
            }
            for (let i = startPage; i <= endPage; i++) { 
                const activeClass = (i === currentPage) ? 'active' : ''; 
                $paginationControls.append('<li class="page-item ' + activeClass + '"><a class="page-link" href="#" data-page="' + i + '">' + i + '</a></li>');
            }
            if (endPage < totalPages) { 
                if (endPage < totalPages - 1) $paginationControls.append('<li class="page-item disabled"><span class="page-link">...</span></li>');
                $paginationControls.append('<li class="page-item"><a class="page-link" href="#" data-page="' + totalPages + '">' + totalPages + '</a></li>');
            }
            if (currentPage < totalPages) { $paginationControls.append('<li class="page-item"><a class="page-link" href="#" data-page="' + (currentPage + 1) + '">&raquo;</a></li>'); } 
            else { $paginationControls.append('<li class="page-item disabled"><span class="page-link">&raquo;</span></li>'); }
        }

        // 페이지네이션 클릭 이벤트 핸들러 - 페이지 번호 클릭 시 해당 페이지의 데이터를 표시
        $paginationControls.on('click', 'a.page-link', function(e) {
            e.preventDefault();
            const page = parseInt($(this).data('page'), 10);
            if (!isNaN(page) && page !== currentPage) {
                currentPage = page;
                renderTable();
                renderPagination();
                $('html, body').animate({ scrollTop: $(".schedule-table-container").offset().top - 70 }, 300);
            }
        });

        // --- 페이지 첫 로딩 시 실행될 로직 ---
        // 페이지 로드 시 바로 오늘 날짜의 전체 스케줄을 로드
        $dateSelectorInput.val(selectedDate); 
        $filterButtons.filter('[data-filter="all"]').removeClass('btn-default').addClass('active btn-success'); 
        updateScheduleDisplayTitle(selectedDate, 'all');
        
        // 초기 데이터가 있으면 바로 표시, 없으면 서버에서 가져오기
        if (initialData && Array.isArray(initialData) && initialData.length > 0) {
            currentSchedules = initialData;
            renderTable();
            renderPagination();
        } else {
            // 초기 데이터가 없으면 서버에서 오늘 날짜의 전체 스케줄을 가져오기
            loadAndDisplaySchedules(selectedDate, 'all');
        }

        $('ul.nav.side-menu a[href$="index5.html"]').closest('li').removeClass('current-page active');
        $('ul.nav.side-menu a[href*="index5.wi"]').closest('li').addClass('current-page');
        $('ul.nav.side-menu a[href*="index5.wi"]').parents('li').addClass('active');

        // --- 버튼 이벤트 핸들러 ---
        
        // DB 저장 버튼 이벤트 핸들러 - 현재 스케줄 데이터를 JSON 형태로 서버에 전송하여 DB에 저장
        $('#saveToDbBtn').on('click', function() {
            const $btn = $(this);
            
            // 현재 표시된 스케줄 데이터가 있는지 확인
            if (!currentSchedules || currentSchedules.length === 0) {
                alert('저장할 스케줄 데이터가 없습니다.');
                return;
            }
            
            // 버튼 비활성화 및 로딩 상태 표시
            $btn.prop('disabled', true);
            $btn.html('<i class="fa fa-spinner fa-spin"></i> 저장 중...');
            
            // 저장할 데이터 준비
            const dataToSave = {
                schedules: currentSchedules,
                date: selectedDate,
                filterType: 'all',
                timestamp: new Date().toISOString()
            };
            console.log("저장을 시작합니다");
            alert(JSON.stringify(currentSchedules[0], null, 2)); // 첫 번째 항목 확인용
            const contextPath = "${pageContext.request.contextPath}";
            const url = contextPath + "/api/flights/save";
            console.log("최종 요청 URL:", url);
            // fetch를 사용하여 JSON 데이터 전송
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(dataToSave)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답 오류: ' + response.status);
                }
                return response.json();
            })
            .then(data => {
            	if (data.status === "success") {
                    alert('스케줄 데이터가 성공적으로 저장되었습니다.');
                } else if (data.status === "Duplicationfail"){
                    alert(data.message || '으로 실패했습니다')
                }            	            	
            	else {
                    alert('저장 실패: ' + (data.message || '알 수 없는 오류'));
                }
            })
            .catch(error => {
                console.error('DB 저장 오류:', error);
                alert('DB 저장 중 오류가 발생했습니다: ' + error.message);
            })
            .finally(() => {
                // 버튼 상태 복원
                $btn.prop('disabled', false);
                $btn.html('<i class="fa fa-database"></i> DB에 저장하기');
            });
        });
        
        // 데이터 동기화 버튼 이벤트 핸들러 - 기존 데이터와 비교하여 새로운 데이터만 추가
        $('#syncDataBtn').on('click', function() {
            const $btn = $(this);
            
            // 버튼 비활성화 및 로딩 상태 표시
            $btn.prop('disabled', true);
            $btn.html('<i class="fa fa-spinner fa-spin"></i> 동기화 중...');
            
            const contextPath = "${pageContext.request.contextPath}";
            
            // 1단계: 현재 데이터 가져오기 (API 사용)
            fetch(contextPath + "/api/flights?" + new URLSearchParams({
                date: selectedDate,
                flightType: 'all'
            }), {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('첫 번째 요청 실패: ' + response.status);
                }
                return response.json();
            })
            .then(currentData => {
                console.log('1단계 - 현재 데이터 가져오기 완료:', currentData);
                
                // 2단계: 새로운 데이터 확인 및 추가 (refreshschedules.wi)
                return fetch(contextPath + "/api/flights/refresh", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    body: JSON.stringify({
                        currentSchedules: currentData,
                        date: selectedDate,
                        filterType: 'all'
                    })
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('두 번째 요청 실패: ' + response.status);
                    }
                    return response.json();
                })
                .then(refreshResult => {
                    console.log('2단계 - 새로운 데이터 확인 완료:', refreshResult);
                    
                    if (refreshResult.status === 'success') {
                        // 새로운 데이터가 있는 경우
                        if (refreshResult.newSchedules && refreshResult.newSchedules.length > 0) {
                            // 기존 스케줄에 새로운 스케줄 추가
                            const addedCount = refreshResult.newSchedules.length;
                            currentSchedules = [...currentSchedules, ...refreshResult.newSchedules];
                            
                            // 테이블과 페이지네이션 다시 렌더링
                            renderTable();
                            renderPagination();
                            
                            alert(refreshResult.message);
                        } else {
                            alert('동기화 완료! 새로운 스케줄이 추가되었습니다');
                        }
                    } else {
                        alert('새로 추가된 스케줄이 없습니다.');
                    }
                });
            })
            .catch(error => {
                console.error('동기화 오류:', error);
                alert('동기화 중 오류가 발생했습니다: ' + error.message);
            })
            .finally(() => {
                // 버튼 상태 복원
                $btn.prop('disabled', false);
                $btn.html('<i class="fa fa-refresh"></i> 최신데이터 동기화');
            });
        });
    });
</script>

</body>
</html>