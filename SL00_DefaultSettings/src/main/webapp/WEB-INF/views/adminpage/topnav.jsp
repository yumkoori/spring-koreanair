<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
request.setCharacterEncoding("UTF-8");

String contextPath = request.getContextPath();
%>  
    <head>
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
    </head>
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
								aria-expanded="false"> <img src="${pageContext.request.contextPath}/images/img.jpg" alt="">John
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
											class="image"><img src="${pageContext.request.contextPath}/images/img.jpg"
												alt="Profile Image" /></span><span><span>John Smith</span><span
												class="time">3 mins ago</span></span><span class="message">Film
												festivals used to be do-or-die moments for movie makers.
												They were where...</span></a></li>
									<li class="nav-item"><a class="dropdown-item"> <span
											class="image"><img src="${pageContext.request.contextPath}/images/img.jpg"
												alt="Profile Image" /></span><span><span>John Smith</span><span
												class="time">3 mins ago</span></span><span class="message">Film
												festivals used to be do-or-die moments for movie makers.
												They were where...</span></a></li>
									<li class="nav-item"><a class="dropdown-item"> <span
											class="image"><img src="${pageContext.request.contextPath}/images/img.jpg"
												alt="Profile Image" /></span><span><span>John Smith</span><span
												class="time">3 mins ago</span></span><span class="message">Film
												festivals used to be do-or-die moments for movie makers.
												They were where...</span></a></li>
									<li class="nav-item"><a class="dropdown-item"> <span
											class="image"><img src="${pageContext.request.contextPath}/images/img.jpg"
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