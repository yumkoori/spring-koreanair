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

    <title>Gentelella Alela! | 정보 검색</title> 

    <link href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/static/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/static/vendors/nprogress/nprogress.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/resources/static/build/css/custom.min.css" rel="stylesheet">

    <style>
      
      .search-section {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 15px;
        padding: 30px;
        margin-bottom: 30px;
        box-shadow: 0 10px 30px rgba(0,0,0,0.1);
      }
      
      .search-section h3 {
        color: white;
        text-shadow: 0 2px 4px rgba(0,0,0,0.3);
        margin-bottom: 20px;
        font-weight: 300;
      }
      
      .search-form-container {
        background: white;
        border-radius: 10px;
        padding: 25px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.1);
      }
      
      .custom-input {
        border-radius: 25px;
        border: 2px solid #e0e6ed;
        padding: 12px 20px;
        transition: all 0.3s ease;
        font-size: 16px;
      }
      
      .custom-input:focus {
        border-color: #667eea;
        box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        outline: none;
      }
      
      .btn-search {
        background: linear-gradient(45deg, #667eea, #764ba2);
        border: none;
        border-radius: 25px;
        padding: 12px 30px;
        color: white;
        font-weight: 500;
        transition: all 0.3s ease;
        box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
      }
      
      .btn-search:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        color: white;
      }
      
      .btn-clear {
        background: transparent;
        border: 2px solid #e0e6ed;
        border-radius: 25px;
        padding: 10px 25px;
        color: #666;
        font-weight: 500;
        transition: all 0.3s ease;
        margin-right: 10px;
      }
      
      .btn-clear:hover {
        border-color: #ff6b6b;
        color: #ff6b6b;
        background: rgba(255, 107, 107, 0.1);
      }
      
      /* 검색 결과 카드 스타일 */
      .results-container {
        margin-top: 20px;
      }
      
      .result-card {
        background: white;
        border-radius: 15px;
        padding: 25px;
        margin-bottom: 20px;
        box-shadow: 0 5px 20px rgba(0,0,0,0.08);
        border: none;
        transition: all 0.3s ease;
        position: relative;
        overflow: hidden;
      }
      
      .result-card:before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 4px;
        background: linear-gradient(45deg, #667eea, #764ba2);
      }
      
      .result-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 15px 40px rgba(0,0,0,0.15);
      }
      
      .user-avatar {
        width: 60px;
        height: 60px;
        background: linear-gradient(45deg, #667eea, #764ba2);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 24px;
        font-weight: bold;
        margin-right: 20px;
        box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
      }
      
      .user-info h4 {
        color: #2c3e50;
        margin-bottom: 5px;
        font-weight: 600;
      }
      
      .user-detail {
        color: #7f8c8d;
        margin-bottom: 8px;
        display: flex;
        align-items: center;
      }
      
      .user-detail i {
        width: 20px;
        margin-right: 10px;
        color: #667eea;
      }
      
      .user-badge {
        background: linear-gradient(45deg, #667eea, #764ba2);
        color: white;
        padding: 6px 16px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: 600;
        display: inline-block;
        margin-top: 5px;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.15);
      }
      
      .d-flex {
        display: flex !important;
      }
      
      .justify-content-between {
        justify-content: space-between !important;
      }
      
      .align-items-start {
        align-items: flex-start !important;
      }
      
      .mb-2 {
        margin-bottom: 0.5rem !important;
      }
      
      .mt-2 {
        margin-top: 0.5rem !important;
      }
      
      .col-md-2, .col-md-10, .col-md-6 {
        position: relative;
        width: 100%;
        padding-right: 15px;
        padding-left: 15px;
      }
      
      @media (min-width: 768px) {
        .col-md-2 {
          flex: 0 0 16.666667%;
          max-width: 16.666667%;
        }
        .col-md-10 {
          flex: 0 0 83.333333%;
          max-width: 83.333333%;
        }
        .col-md-6 {
          flex: 0 0 50%;
          max-width: 50%;
        }
      }
      
      /* 로딩 애니메이션 개선 */
      .loading-container {
        text-align: center;
        padding: 40px;
      }
      
      .loading-spinner {
        width: 50px;
        height: 50px;
        border: 4px solid #f3f3f3;
        border-top: 4px solid #667eea;
        border-radius: 50%;
        animation: spin 1s linear infinite;
        margin: 0 auto 20px;
      }
      
      @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
      }
      
      .loading-text {
        color: #667eea;
        font-weight: 500;
      }
      
      /* 빈 결과 스타일 */
      .empty-state {
        text-align: center;
        padding: 60px 20px;
        color: #7f8c8d;
      }
      
      .empty-state i {
        font-size: 64px;
        color: #ecf0f1;
        margin-bottom: 20px;
      }
      
      .empty-state h4 {
        color: #2c3e50;
        margin-bottom: 10px;
      }
      
      /* 반응형 디자인 */
      @media (max-width: 768px) {
        .search-section {
          padding: 20px;
        }
        
        .search-form-container {
          padding: 20px;
        }
        
        .result-card {
          padding: 20px;
        }
        
        .user-avatar {
          width: 50px;
          height: 50px;
          font-size: 20px;
          margin-right: 15px;
        }
      }
      
      /* 애니메이션 효과 */
      .fade-in {
        animation: fadeIn 0.5s ease-in;
      }
      
      @keyframes fadeIn {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
      }
    </style>
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>Gentelella Alela!</span></a>
            </div>

            <div class="clearfix"></div>

            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>John Doe</h2>
              </div>
            </div>
            <br />

            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>General</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-home"></i> Home <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="index.html">Dashboard</a></li>
                      <li><a href="index2.html">Dashboard2</a></li>
                      <li><a href="index3.html">Dashboard3</a></li>
                      <li><a href="index4.html">좌석관리</a></li>
                      <li><a href="index5.html">비행스케줄표</a></li>
                      <li><a href="index6.html">정보 검색</a></li> {/* 새 페이지 링크 추가 */}
                    </ul>
                  </li>
                  <li><a><i class="fa fa-edit"></i> Forms <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="form.html">General Form</a></li>
                      <li><a href="form_advanced.html">Advanced Components</a></li>
                      <li><a href="form_validation.html">Form Validation</a></li>
                      <li><a href="form_wizards.html">Form Wizard</a></li>
                      <li><a href="form_upload.html">Form Upload</a></li>
                      <li><a href="form_buttons.html">Form Buttons</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-desktop"></i> UI Elements <span class="fa fa-chevron-down"></span></a>
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
                    </ul>
                  </li>
                  <li><a><i class="fa fa-table"></i> Tables <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="tables.html">Tables</a></li>
                      <li><a href="tables_dynamic.html">Table Dynamic</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-bar-chart-o"></i> Data Presentation <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="chartjs.html">Chart JS</a></li>
                      <li><a href="chartjs2.html">Chart JS2</a></li>
                      <li><a href="morisjs.html">Moris JS</a></li>
                      <li><a href="echarts.html">ECharts</a></li>
                      <li><a href="other_charts.html">Other Charts</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-clone"></i>Layouts <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="fixed_sidebar.html">Fixed Sidebar</a></li>
                      <li><a href="fixed_footer.html">Fixed Footer</a></li>
                    </ul>
                  </li>
                </ul>
              </div>
              <div class="menu_section">
                <h3>Live On</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-bug"></i> Additional Pages <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="e_commerce.html">E-commerce</a></li>
                      <li><a href="projects.html">Projects</a></li>
                      <li><a href="project_detail.html">Project Detail</a></li>
                      <li><a href="contacts.html">Contacts</a></li>
                      <li><a href="profile.html">Profile</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-windows"></i> Extras <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="page_403.html">403 Error</a></li>
                      <li><a href="page_404.html">404 Error</a></li>
                      <li><a href="page_500.html">500 Error</a></li>
                      <li><a href="plain_page.html">Plain Page</a></li>
                      <li><a href="login.html">Login Page</a></li>
                      <li><a href="pricing_tables.html">Pricing Tables</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-sitemap"></i> Multilevel Menu <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                        <li><a href="#level1_1">Level One</a>
                        <li><a>Level One<span class="fa fa-chevron-down"></span></a>
                          <ul class="nav child_menu">
                            <li class="sub_menu"><a href="level2.html">Level Two</a></li>
                            <li><a href="#level2_1">Level Two</a></li>
                            <li><a href="#level2_2">Level Two</a></li>
                          </ul>
                        </li>
                        <li><a href="#level1_2">Level One</a></li>
                    </ul>
                  </li>                  
                  <li><a href="javascript:void(0)"><i class="fa fa-laptop"></i> Landing Page <span class="label label-success pull-right">Coming Soon</span></a></li>
                </ul>
              </div>
            </div>
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            </div>
        </div>

        <div class="top_nav">
          <div class="nav_menu">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>
              <nav class="nav navbar-nav">
              <ul class=" navbar-right">
                <li class="nav-item dropdown open" style="padding-left: 15px;">
                  <a href="javascript:;" class="user-profile dropdown-toggle" aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">
                    <img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="">John Doe
                  </a>
                  <div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item"  href="javascript:;"> Profile</a>
                      <a class="dropdown-item"  href="javascript:;">
                        <span class="badge bg-red pull-right">50%</span>
                        <span>Settings</span>
                      </a>
                  <a class="dropdown-item"  href="javascript:;">Help</a>
                    <a class="dropdown-item"  href="login.html"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                  </div>
                </li>

                <li role="presentation" class="nav-item dropdown open">
                  <a href="javascript:;" class="dropdown-toggle info-number" id="navbarDropdown1" data-toggle="dropdown" aria-expanded="false">
                    <i class="fa fa-envelope-o"></i>
                    <span class="badge bg-green">6</span>
                  </a>
                  <ul class="dropdown-menu list-unstyled msg_list" role="menu" aria-labelledby="navbarDropdown1">
                    <li class="nav-item">
                      <a class="dropdown-item">
                        <span class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li class="nav-item">
                      <a class="dropdown-item">
                        <span class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li class="nav-item">
                      <a class="dropdown-item">
                        <span class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li class="nav-item">
                      <a class="dropdown-item">
                        <span class="image"><img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="Profile Image" /></span>
                        <span>
                          <span>John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li class="nav-item">
                      <div class="text-center">
                        <a class="dropdown-item">
                          <strong>See All Alerts</strong>
                          <i class="fa fa-angle-right"></i>
                        </a>
                      </div>
                    </li>
                  </ul>
                </li>
              </ul>
            </nav>
          </div>
        </div>
        <div class="right_col" role="main">
          <div class="">
          
          
            <div class="row">
              <div class="col-md-12">
                <div class="search-section">
                  <h3><i class="fa fa-search"></i> 사용자 정보 검색</h3>
                  <div class="search-form-container">
                    <form id="searchFormByName">
                      <div class="row align-items-end">
                        <div class="col-md-8">
                          <label for="searchInputByName" class="form-label" style="color: #2c3e50; font-weight: 500; margin-bottom: 10px;">
                            <i class="fa fa-user"></i> 검색할 이름
                          </label>
                          <input type="text" 
                                 id="searchInputByName" 
                                 class="form-control custom-input" 
                                 placeholder="이름을 입력하고 검색해보세요..."
                                 required>
                        </div>
                        <div class="col-md-4">
                          <div class="btn-group w-100" style="margin-top: 25px;">
                            <button type="button" class="btn btn-clear" id="clearSearchByNameButton">
                              <i class="fa fa-refresh"></i> 초기화
                            </button>
                            <button type="submit" class="btn btn-search">
                              <i class="fa fa-search"></i> 검색
                            </button>
                          </div>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-md-12">
                <div class="results-container">
                  <div id="searchByNameLoadingIndicator" class="loading-container" style="display: none;">
                    <div class="loading-spinner"></div>
                    <div class="loading-text">사용자 정보를 검색하고 있습니다...</div>
                  </div>
                  <div id="searchByNameResultsContainer">
                    <div class="empty-state">
                      <i class="fa fa-search"></i>
                      <h4>검색을 시작해보세요</h4>
                      <p>위의 검색창에 이름을 입력하고 검색 버튼을 눌러주세요.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            

          </div>
        </div>
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/static/vendors/jquery/dist/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/static/vendors/fastclick/lib/fastclick.js"></script>
    <script src="${pageContext.request.contextPath}/resources/static/vendors/nprogress/nprogress.js"></script>
    <script src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/static/vendors/moment/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/static/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
    
    <script src="${pageContext.request.contextPath}/resources/static/build/js/custom.js"></script>

    <script>
    $(document).ready(function() {
        // 이름 검색 기능 관련 jQuery 객체 캐싱
        const $searchInputByName = $('#searchInputByName');
        const $searchResultsByNameContainer = $('#searchByNameResultsContainer');
        const $searchByNameLoadingIndicator = $('#searchByNameLoadingIndicator');
        const $searchFormByName = $('#searchFormByName');

        // 이름 검색 폼 제출 시 이벤트 처리
        $searchFormByName.on('submit', function(event) {
            event.preventDefault(); // 폼의 기본 제출 동작(페이지 새로고침) 방지
            performNameSearch();
        });

        // 이름 검색 초기화 버튼 클릭 이벤트
        $('#clearSearchByNameButton').on('click', function() {
            $searchInputByName.val(''); // 입력 필드 비우기
            $searchResultsByNameContainer.html(`
                <div class="empty-state">
                    <i class="fa fa-search"></i>
                    <h4>검색을 시작해보세요</h4>
                    <p>위의 검색창에 이름을 입력하고 검색 버튼을 눌러주세요.</p>
                </div>
            `); // 결과 영역 초기화
        });
        
        function performNameSearch() {
            const searchTerm = $searchInputByName.val().trim();

            if (searchTerm === "") {
                alert("검색할 이름을 입력해주세요.");
                $searchInputByName.focus();
                return;
            }

            $searchByNameLoadingIndicator.show();
            $searchResultsByNameContainer.html(''); // 이전 검색 결과 지우기

            // AJAX를 사용하여 서버에 이름 검색 요청
            const contextPath = "${pageContext.request.contextPath}";
            const url = contextPath + '/users/search';
            
            $.ajax({
                url: url, // 데이터를 검색할 서버 측 JSP 파일 (별도 구현 필요)
                type: 'GET',
                data: { name: searchTerm }, // 'name' 파라미터로 검색어 전달
                dataType: 'json',
                cache: false,
                                success: function(response) {
                    $searchByNameLoadingIndicator.hide();

                    // 응답 구조에 맞게 데이터 추출
                    const results = response.results || response;
                    
                    if (results && results.length > 0) {
                        let htmlContent = '';
                        
                        results.forEach(function(item, index) {
                            // UserInfoDTO의 실제 필드명에 맞춰서 데이터 추출
                            const koName = item.koname || '이름 정보 없음';
                            const enName = item.enname || 'Unknown';
                            const userEmail = item.email || '이메일 정보 없음';
                            const phoneNumber = item.phonenumber || '연락처 정보 없음';
                            const address = item.address || '주소 정보 없음';
                            const grade = item.grade || 'general';
                            const userId = item.userid || '';
                            const gender = item.gender === 'M' ? '남성' : item.gender === 'F' ? '여성' : '미설정';
                            const birthdate = item.birthdate || '생년월일 정보 없음';
                            const userInitial = koName.charAt(0).toUpperCase();
                            
                            // 등급에 따른 배지 색상 설정
                            let gradeColor = '#667eea';
                            let gradeText = grade;
                            if (grade === 'skypass') {
                                gradeColor = '#f39c12';
                                gradeText = 'SkyPass';
                            } else if (grade === 'gold') {
                                gradeColor = '#e67e22';
                                gradeText = 'Gold';
                            } else if (grade === 'silver') {
                                gradeColor = '#95a5a6';
                                gradeText = 'Silver';
                            }
                            
                            // 아름다운 사용자 카드 HTML 생성
                            htmlContent += '<div class="result-card" style="' +
                                'animation-delay: ' + (index * 0.1) + 's; ' +
                                'margin-bottom: 20px; ' +
                                'padding: 25px; ' +
                                'background: white; ' +
                                'border-radius: 15px; ' +
                                'box-shadow: 0 5px 20px rgba(0,0,0,0.08); ' +
                                'border-top: 4px solid #667eea; ' +
                                'transition: all 0.3s ease;' +
                                '" onmouseover="this.style.transform=\'translateY(-5px)\'; this.style.boxShadow=\'0 15px 40px rgba(0,0,0,0.15)\'" ' +
                                'onmouseout="this.style.transform=\'translateY(0)\'; this.style.boxShadow=\'0 5px 20px rgba(0,0,0,0.08)\'">' +
                                
                                '<div style="display: flex; align-items: flex-start; gap: 20px;">' +
                                    '<div style="flex: 0 0 60px;">' +
                                        '<div style="' +
                                            'width: 60px; ' +
                                            'height: 60px; ' +
                                            'background: linear-gradient(45deg, #667eea, #764ba2); ' +
                                            'border-radius: 50%; ' +
                                            'display: flex; ' +
                                            'align-items: center; ' +
                                            'justify-content: center; ' +
                                            'color: white; ' +
                                            'font-size: 24px; ' +
                                            'font-weight: bold; ' +
                                            'box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);' +
                                        '">' + userInitial + '</div>' +
                                    '</div>' +
                                    
                                    '<div style="flex: 1;">' +
                                        '<div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 15px;">' +
                                            '<h4 style="margin: 0; color: #2c3e50; font-size: 20px; font-weight: 600;">' +
                                                '<i class="fa fa-user" style="margin-right: 8px; color: #667eea;"></i>' + koName +
                                            '</h4>' +
                                            '<span style="' +
                                                'background: ' + gradeColor + '; ' +
                                                'color: white; ' +
                                                'padding: 6px 16px; ' +
                                                'border-radius: 20px; ' +
                                                'font-size: 12px; ' +
                                                'font-weight: 600; ' +
                                                'text-transform: uppercase; ' +
                                                'letter-spacing: 0.5px; ' +
                                                'box-shadow: 0 2px 8px rgba(0,0,0,0.15);' +
                                            '">' + gradeText + '</span>' +
                                        '</div>' +
                                        
                                        '<div style="margin-bottom: 8px; color: #7f8c8d; display: flex; align-items: center;">' +
                                            '<i class="fa fa-globe" style="width: 20px; margin-right: 10px; color: #667eea;"></i>' +
                                            '<span>' + enName + '</span>' +
                                        '</div>' +
                                        
                                        '<div style="margin-bottom: 8px; color: #7f8c8d; display: flex; align-items: center;">' +
                                            '<i class="fa fa-id-card" style="width: 20px; margin-right: 10px; color: #667eea;"></i>' +
                                            '<span>ID: ' + userId + '</span>' +
                                        '</div>' +
                                        
                                        '<div style="margin-bottom: 8px; color: #7f8c8d; display: flex; align-items: center;">' +
                                            '<i class="fa fa-envelope" style="width: 20px; margin-right: 10px; color: #667eea;"></i>' +
                                            '<span>' + userEmail + '</span>' +
                                        '</div>' +
                                        
                                        '<div style="margin-bottom: 8px; color: #7f8c8d; display: flex; align-items: center;">' +
                                            '<i class="fa fa-phone" style="width: 20px; margin-right: 10px; color: #667eea;"></i>' +
                                            '<span>' + phoneNumber + '</span>' +
                                        '</div>' +
                                        
                                        '<div style="margin-bottom: 8px; color: #7f8c8d; display: flex; align-items: center;">' +
                                            '<i class="fa fa-map-marker" style="width: 20px; margin-right: 10px; color: #667eea;"></i>' +
                                            '<span>' + address + '</span>' +
                                        '</div>' +
                                        
                                        '<div style="display: flex; gap: 30px; margin-top: 15px; padding-top: 15px; border-top: 1px solid #ecf0f1;">' +
                                            '<div style="color: #7f8c8d; display: flex; align-items: center;">' +
                                                '<i class="fa fa-birthday-cake" style="width: 20px; margin-right: 10px; color: #667eea;"></i>' +
                                                '<span>' + birthdate + '</span>' +
                                            '</div>' +
                                            '<div style="color: #7f8c8d; display: flex; align-items: center;">' +
                                                '<i class="fa fa-venus-mars" style="width: 20px; margin-right: 10px; color: #667eea;"></i>' +
                                                '<span>' + gender + '</span>' +
                                            '</div>' +
                                        '</div>' +
                                    '</div>' +
                                '</div>' +
                            '</div>';
                        });
                        
                        $searchResultsByNameContainer.html(htmlContent);
                    } else {
                        $searchResultsByNameContainer.html(`
                            <div class="empty-state">
                                <i class="fa fa-user-times"></i>
                                <h4>검색 결과가 없습니다</h4>
                                <p>"${searchTerm}"에 대한 검색 결과를 찾을 수 없습니다.<br>다른 이름으로 검색해보세요.</p>
                            </div>
                        `);
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $searchByNameLoadingIndicator.hide();
                    $searchResultsByNameContainer.html(`
                        <div class="empty-state">
                            <i class="fa fa-exclamation-triangle" style="color: #e74c3c;"></i>
                            <h4 style="color: #e74c3c;">오류가 발생했습니다</h4>
                            <p>이름 검색 중 문제가 발생했습니다.<br>잠시 후 다시 시도해주세요.</p>
                        </div>
                    `);
                }
            });
        }

        // index6.jsp 페이지에 해당하는 사이드바 메뉴 활성화
        $('ul.nav.side-menu a[href="index6.html"]').closest('li').addClass('current-page active');
        // Gentelella 테마에서 상위 메뉴도 active 상태로 만들기 위함 (<a> 태그가 직접 li의 자식이 아닐 경우)
        $('ul.nav.side-menu a[href="index6.html"]').parents('li').addClass('active');


        // (선택 사항) 만약 daterangepicker 같은 다른 JS 라이브러리 초기화가 필요하면 여기에 추가합니다.
        // 예: $('#myDatepicker').daterangepicker();
        // 이 페이지에서는 daterangepicker를 사용하지 않으므로 관련 초기화 코드는 없습니다.

    });
    </script>

  </body>
</html>
</html>