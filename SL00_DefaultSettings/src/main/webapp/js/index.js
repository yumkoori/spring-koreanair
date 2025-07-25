document.addEventListener('DOMContentLoaded', function() {
    // 탭 기능 구현 (메인 페이지에만 존재)
    const tabBtns = document.querySelectorAll('.booking-tab-btn');
    const bookingContents = document.querySelectorAll('.booking-content');

    if (tabBtns.length > 0) {
    tabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const targetTab = btn.getAttribute('data-tab');
            
            // 모든 탭 버튼에서 active 클래스 제거
            tabBtns.forEach(b => b.classList.remove('active'));
            // 클릭된 탭 버튼에 active 클래스 추가
            btn.classList.add('active');
            
            // 모든 콘텐츠에서 active 클래스 제거
            bookingContents.forEach(content => content.classList.remove('active'));
            // 해당 콘텐츠에 active 클래스 추가
                const targetElement = document.getElementById(targetTab);
                if (targetElement) {
                    targetElement.classList.add('active');
                }
        });
    });
    }

    // 슬라이더 기능 구현 (메인 페이지에만 존재)
    const slides = document.querySelectorAll('.slide');
    const dots = document.querySelectorAll('.dot');
    const prevBtn = document.querySelector('.prev');
    const nextBtn = document.querySelector('.next');
    
    if (slides.length > 0 && dots.length > 0 && prevBtn && nextBtn) {
    let currentSlide = 0;
    const slideCount = slides.length;
    
    // 슬라이드 이동 함수
    function moveToSlide(index) {
        // 현재 활성화된 슬라이드와 닷 비활성화
        slides[currentSlide].classList.remove('active');
        dots[currentSlide].classList.remove('active');
        
        // 새로운 슬라이드와 닷 활성화
        currentSlide = index;
        
        // 슬라이드 인덱스 조정 (순환)
        if (currentSlide < 0) {
            currentSlide = slideCount - 1;
        } else if (currentSlide >= slideCount) {
            currentSlide = 0;
        }
        
        slides[currentSlide].classList.add('active');
        dots[currentSlide].classList.add('active');
    }
    
    // 다음 슬라이드로 이동
    function nextSlide() {
        moveToSlide(currentSlide + 1);
    }
    
    // 이전 슬라이드로 이동
    function prevSlide() {
        moveToSlide(currentSlide - 1);
    }
    
    // 이벤트 리스너 추가
    prevBtn.addEventListener('click', prevSlide);
    nextBtn.addEventListener('click', nextSlide);
    
    // 닷 클릭 이벤트
    dots.forEach((dot, index) => {
        dot.addEventListener('click', () => {
            moveToSlide(index);
        });
    });
    
    // 자동 슬라이드 기능
    let slideInterval = setInterval(nextSlide, 5000);
    
    // 슬라이드에 마우스를 올리면 자동 슬라이드 멈춤
    const bannerSlider = document.querySelector('.banner-slider');
    
        if (bannerSlider) {
    bannerSlider.addEventListener('mouseenter', () => {
        clearInterval(slideInterval);
    });
    
    // 슬라이드에서 마우스가 나가면 자동 슬라이드 재시작
    bannerSlider.addEventListener('mouseleave', () => {
        slideInterval = setInterval(nextSlide, 5000);
    });
        }
    }

    // 출발지/도착지 교환 버튼 기능 (메인 페이지에만 존재)
    const swapBtns = document.querySelectorAll('.swap-route-btn');
    if (swapBtns.length > 0) {
    swapBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const routeInputs = btn.parentElement.querySelectorAll('.airport-input');
            if (routeInputs.length === 2) {
                const departure = routeInputs[0];
                const arrival = routeInputs[1];
                
                // 코드와 이름 교환
                const tempCode = departure.querySelector('.airport-code').textContent;
                const tempName = departure.querySelector('.airport-name').textContent;
                
                departure.querySelector('.airport-code').textContent = arrival.querySelector('.airport-code').textContent;
                departure.querySelector('.airport-name').textContent = arrival.querySelector('.airport-name').textContent;
                
                arrival.querySelector('.airport-code').textContent = tempCode;
                arrival.querySelector('.airport-name').textContent = tempName;
            }
        });
    });
    }

    // 스크롤 이벤트 - 헤더 애니메이션 (모든 페이지에서 실행)
    const header = document.querySelector('header.airline-header');
    const headerTop = document.querySelector('.airline-header-top');
    const dropdownMenus = document.querySelectorAll('.airline-dropdown-menu');
    let lastScrollTop = 0;
    let scrollDirection = 'none';
    
    // 드롭다운 메뉴의 위치를 헤더 높이에 맞게 조정하는 함수
    function adjustDropdownPositions() {
        if (!header) return;
        const headerHeight = header.offsetHeight;
        const viewportWidth = window.innerWidth;
        
        // 스크롤 위치에 따라 드롭다운 메뉴 위치 조정
        dropdownMenus.forEach(menu => {
            // 기본 스타일 초기화
            menu.style.left = '';
            menu.style.right = '';
            menu.style.transform = '';
            menu.style.width = '';
            menu.style.maxWidth = '';
            menu.style.minWidth = '';
            
            if (viewportWidth <= 840) {
                // 태블릿 및 작은 화면에서 (400px 조건 제거하고 840px로 통합)
                menu.style.top = (headerHeight - 10) + 'px';
                menu.style.left = '50%';
                menu.style.transform = 'translateX(-50%) translateY(5px)';
                menu.style.width = '95vw';
                menu.style.maxWidth = '95vw';
                menu.style.minWidth = '320px';
            } else if (viewportWidth <= 900) {
                // 태블릿 화면에서
                menu.style.top = (headerHeight - 6) + 'px';
                menu.style.left = '50%';
                menu.style.transform = 'translateX(-50%) translateY(5px)';
                menu.style.width = '90vw';
                menu.style.maxWidth = '90vw';
                menu.style.minWidth = '320px';
            } else {
                // 데스크톱 화면에서
                menu.style.top = (headerHeight - 6) + 'px';
                menu.style.left = '50%';
                menu.style.transform = 'translateX(-50%) translateY(5px)';
                menu.style.width = 'clamp(320px, 90vw, 1100px)';
                menu.style.maxWidth = 'min(1100px, 95vw)';
                menu.style.minWidth = '320px';
            }
        });
        
        // 브릿지 영역도 함께 조정
        updateBridgePosition(headerHeight);
    }
    
    // 브릿지 위치 업데이트 함수 분리
    function updateBridgePosition(headerHeight) {
        const viewportWidth = window.innerWidth;
        let bridgeTop;
        
        if (viewportWidth <= 840) {
            bridgeTop = headerHeight - 10;
        } else if (viewportWidth <= 900) {
            bridgeTop = headerHeight - 6;
        } else {
            bridgeTop = headerHeight - 16;
        }
        
        const style = document.createElement('style');
        style.textContent = `
            .airline-nav-item::after {
                top: ${bridgeTop}px !important;
                width: 100vw;
            }
        `;
        
        // 기존 스타일 제거 후 새로운 스타일 추가
        const existingStyle = document.querySelector('#dynamic-bridge-style');
        if (existingStyle) {
            existingStyle.remove();
        }
        style.id = 'dynamic-bridge-style';
        document.head.appendChild(style);
    }
    
    // 드롭다운 메뉴가 화면 밖으로 나가지 않도록 실시간 체크하는 함수
    function checkDropdownBounds() {
        const viewportWidth = window.innerWidth;
        
        dropdownMenus.forEach(menu => {
            if (menu.style.visibility === 'visible' && menu.style.opacity === '1') {
                // 메뉴가 보이는 상태일 때만 체크
                setTimeout(() => {
                    const menuRect = menu.getBoundingClientRect();
                    const margin = 10;
                    
                    if (menuRect.left < margin) {
                        // 왼쪽으로 잘릴 때
                        menu.style.left = margin + 'px';
                        menu.style.transform = 'translateY(5px)';
                    } else if (menuRect.right > viewportWidth - margin) {
                        // 오른쪽으로 잘릴 때
                        menu.style.left = 'auto';
                        menu.style.right = margin + 'px';
                        menu.style.transform = 'translateY(5px)';
                    } else if (menu.style.left !== '50%') {
                        // 정상 범위에 있으면 중앙 정렬로 복원
                        menu.style.left = '50%';
                        menu.style.right = 'auto';
                        menu.style.transform = 'translateX(-50%) translateY(5px)';
                    }
                }, 10); // 약간의 지연을 두어 렌더링 완료 후 체크
            }
        });
    }
    
    // 초기 위치 설정
    adjustDropdownPositions();
    
    // 창 크기 변경 시 위치 조정 - 디바운스 적용
    let resizeTimeout;
    window.addEventListener('resize', () => {
        clearTimeout(resizeTimeout);
        resizeTimeout = setTimeout(() => {
            adjustDropdownPositions();
            // 현재 열려있는 드롭다운이 있다면 위치 재조정
            checkDropdownBounds();
        }, 100); // 100ms 디바운스
    });
    
    // 추가로 실시간 체크를 위한 이벤트
    window.addEventListener('resize', () => {
        // 즉시 실행 (디바운스와 별도)
        checkDropdownBounds();
    });
    
    // 스크롤 이벤트 (헤더가 있는 모든 페이지에서 실행)
    if (header && headerTop) {
    window.addEventListener('scroll', function() {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        
        // 스크롤 방향 감지
        if (scrollTop > lastScrollTop) {
            scrollDirection = 'down';
        } else {
            scrollDirection = 'up';
        }
        
        // 스크롤 위치에 따른 헤더 스타일 변경
        if (scrollTop > 100) {
            header.classList.add('scrolled');
            
            // 스크롤 다운 시 상단 메뉴 숨기기
            if (scrollDirection === 'down' && scrollTop > 200) {
                headerTop.style.transform = 'translateY(-100%)';
                headerTop.style.opacity = '0';
                header.style.transform = 'translateY(-' + headerTop.offsetHeight + 'px)';
            } 
            // 스크롤 업 시 상단 메뉴 다시 보이기
            else if (scrollDirection === 'up') {
                headerTop.style.transform = 'translateY(0)';
                headerTop.style.opacity = '1';
                header.style.transform = 'translateY(0)';
            }
        } else {
            header.classList.remove('scrolled');
            headerTop.style.transform = 'translateY(0)';
            headerTop.style.opacity = '1';
            header.style.transform = 'translateY(0)';
        }
        
        // 헤더 상태 변화에 따라 드롭다운 메뉴 위치 조정
        adjustDropdownPositions();
        
        lastScrollTop = scrollTop <= 0 ? 0 : scrollTop; // iOS 바운스 효과 방지
    });
    }

    // 폼 유효성 검사 (메인 페이지에만 존재)
    const bookingForms = document.querySelectorAll('.booking-form');
    
    if (bookingForms.length > 0) {
    bookingForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            
            let isValid = true;
            const inputs = this.querySelectorAll('input[type="text"], input[type="date"]');
            
            inputs.forEach(input => {
                if (input.value.trim() === '') {
                    isValid = false;
                    input.classList.add('error');
                } else {
                    input.classList.remove('error');
                }
            });
            
            if (isValid) {
                // 실제 제출 코드 (실제 구현 시 서버로 전송)
                alert('항공권 검색을 시작합니다.');
                // form.submit();
            } else {
                alert('모든 필수 항목을 입력해주세요.');
            }
        });
    });
    }

    // 여행 타입 버튼 기능 (메인 페이지에만 존재)
    const tripTypeBtns = document.querySelectorAll('.trip-type-btn');
    if (tripTypeBtns.length > 0) {
    tripTypeBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            tripTypeBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
        });
    });
    }
    
    // 상태 버튼 기능 (메인 페이지에만 존재)
    const statusBtns = document.querySelectorAll('.status-btn');
    if (statusBtns.length > 0) {
    statusBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            statusBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
        });
    });
    }

    // 드롭다운 메뉴 기능 (모든 페이지에서 실행)
    const navItems = document.querySelectorAll('.airline-nav-item.dropdown');
    
    if (navItems.length > 0) {
    navItems.forEach(navItem => {
        const navLink = navItem.querySelector('.airline-nav-link');
        const dropdownMenu = navItem.querySelector('.airline-dropdown-menu');
        let hoverTimeout;
        let isMouseOverDropdown = false;
        let isMouseOverNavItem = false;
        
        // 마우스 호버 이벤트 - 네비게이션 아이템
        navItem.addEventListener('mouseenter', () => {
            clearTimeout(hoverTimeout);
            isMouseOverNavItem = true;
            dropdownMenu.style.opacity = '1';
            dropdownMenu.style.visibility = 'visible';
            dropdownMenu.style.transform = 'translateX(-50%) translateY(0)';
            
            // 메뉴가 표시된 후 위치 체크
            checkDropdownBounds();
        });
        
        navItem.addEventListener('mouseleave', () => {
            isMouseOverNavItem = false;
            // 드롭다운 메뉴에 마우스가 없을 때만 숨김 처리
            if (!isMouseOverDropdown) {
                hoverTimeout = setTimeout(() => {
                    if (!isMouseOverNavItem && !isMouseOverDropdown) {
                        dropdownMenu.style.opacity = '0';
                        dropdownMenu.style.visibility = 'hidden';
                        dropdownMenu.style.transform = 'translateX(-50%) translateY(-10px)';
                        
                        // 메뉴가 사라질 때 위치 초기화
                        setTimeout(() => {
                            if (dropdownMenu.style.visibility === 'hidden') {
                                dropdownMenu.style.left = '50%';
                                dropdownMenu.style.right = 'auto';
                                dropdownMenu.style.transform = 'translateX(-50%) translateY(5px)';
                            }
                        }, 150); // 애니메이션 완료 후 초기화
                    }
                }, 200); // 지연 시간을 200ms로 증가
            }
        });
        
        // 드롭다운 메뉴 자체에 대한 마우스 이벤트
        dropdownMenu.addEventListener('mouseenter', () => {
            clearTimeout(hoverTimeout);
            isMouseOverDropdown = true;
        });
        
        dropdownMenu.addEventListener('mouseleave', () => {
            isMouseOverDropdown = false;
            // 네비게이션 아이템에 마우스가 없을 때만 숨김 처리
            if (!isMouseOverNavItem) {
                hoverTimeout = setTimeout(() => {
                    if (!isMouseOverNavItem && !isMouseOverDropdown) {
                        dropdownMenu.style.opacity = '0';
                        dropdownMenu.style.visibility = 'hidden';
                        dropdownMenu.style.transform = 'translateX(-50%) translateY(-10px)';
                        
                        // 메뉴가 사라질 때 위치 초기화
                        setTimeout(() => {
                            if (dropdownMenu.style.visibility === 'hidden') {
                                dropdownMenu.style.left = '50%';
                                dropdownMenu.style.right = 'auto';
                                dropdownMenu.style.transform = 'translateX(-50%) translateY(5px)';
                            }
                        }, 150); // 애니메이션 완료 후 초기화
                    }
                }, 100);
            }
        });
        
        // 키보드 접근성
        navLink.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                const isVisible = dropdownMenu.style.visibility === 'visible';
                
                if (isVisible) {
                    dropdownMenu.style.opacity = '0';
                    dropdownMenu.style.visibility = 'hidden';
                } else {
                    // 다른 드롭다운 메뉴 닫기
                    navItems.forEach(item => {
                        if (item !== navItem) {
                            const otherMenu = item.querySelector('.airline-dropdown-menu');
                            otherMenu.style.opacity = '0';
                            otherMenu.style.visibility = 'hidden';
                        }
                    });
                    
                    dropdownMenu.style.opacity = '1';
                    dropdownMenu.style.visibility = 'visible';
                    dropdownMenu.style.transform = 'translateX(-50%) translateY(0)';
                    
                    // 첫 번째 링크에 포커스
                    const firstLink = dropdownMenu.querySelector('a');
                    if (firstLink) {
                        firstLink.focus();
                    }
                }
            }
        });
        
        // ESC 키로 드롭다운 닫기
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape') {
                dropdownMenu.style.opacity = '0';
                dropdownMenu.style.visibility = 'hidden';
                navLink.focus();
            }
        });
        
        // 드롭다운 외부 클릭 시 닫기
        document.addEventListener('click', (e) => {
            if (!navItem.contains(e.target)) {
                dropdownMenu.style.opacity = '0';
                dropdownMenu.style.visibility = 'hidden';
            }
        });
    });
    
    // 모바일에서 터치 이벤트 처리
    if ('ontouchstart' in window) {
        navItems.forEach(navItem => {
            const navLink = navItem.querySelector('.airline-nav-link');
            const dropdownMenu = navItem.querySelector('.airline-dropdown-menu');
            
            navLink.addEventListener('touchstart', (e) => {
                e.preventDefault();
                
                // 다른 드롭다운 메뉴 닫기
                navItems.forEach(item => {
                    if (item !== navItem) {
                        const otherMenu = item.querySelector('.airline-dropdown-menu');
                        otherMenu.style.opacity = '0';
                        otherMenu.style.visibility = 'hidden';
                    }
                });
                
                // 현재 드롭다운 토글
                const isVisible = dropdownMenu.style.visibility === 'visible';
                if (isVisible) {
                    dropdownMenu.style.opacity = '0';
                    dropdownMenu.style.visibility = 'hidden';
                } else {
                    dropdownMenu.style.opacity = '1';
                    dropdownMenu.style.visibility = 'visible';
                    dropdownMenu.style.transform = 'translateX(-50%) translateY(0)';
                }
            });
        });
    }
    }
    
    // 예약 조회 AJAX 처리 (메인 페이지에만 존재)
    const reservationLookupForm = document.querySelector('#checkin .checkin-form');
    if (reservationLookupForm) {
        reservationLookupForm.addEventListener('submit', function(e) {
            e.preventDefault();
            console.log('예약 조회 폼 제출 이벤트 - AJAX 처리 시작');
            
            // 필수 동의 체크
            const agreeCheckbox = this.querySelector('input[type="checkbox"]');
            if (agreeCheckbox && !agreeCheckbox.checked) {
                alert('[필수] 항목에 동의해주셔야 조회가 가능합니다.');
                return;
            }
            
            // 에러 메시지 숨기기
            const errorBox = document.getElementById('bookingErrorBox');
            if (errorBox) errorBox.classList.add('hidden');
            
            // AJAX 요청
            const formData = new FormData(this);
            console.log('FormData 생성 완료, AJAX 요청 시작');
            
            fetch(window.contextPath + '/reservation/lookup.htm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                    'Accept': 'application/json'
                },
                body: new URLSearchParams(formData)
            })
            .then(response => {
                console.log('응답 받음:', response);
                return response.json();
            })
            .then(data => {
                console.log('JSON 데이터:', data);
                if (data.success) {
                    const redirectUrl = window.contextPath + '/' + data.redirectUrl;
                    console.log('리다이렉트 URL:', redirectUrl);
                    window.location.href = redirectUrl;
                } else {
                    const errorMessageElement = document.getElementById('bookingErrorMessage');
                    if (errorBox && errorMessageElement) {
                        errorMessageElement.textContent = data.error || '알 수 없는 오류가 발생했습니다.';
                        errorBox.classList.remove('hidden');
                    }
                }
            })
            .catch(error => {
                console.error('AJAX 오류:', error);
                const errorMessageElement = document.getElementById('bookingErrorMessage');
                if (errorBox && errorMessageElement) {
                    errorMessageElement.textContent = '조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
                    errorBox.classList.remove('hidden');
                }
            });
        });
    }

    // 체크인 AJAX 처리 (메인 페이지에만 존재)
    const checkinLookupForm = document.querySelector('#checkinLookupForm');
    if (checkinLookupForm) {
        checkinLookupForm.addEventListener('submit', function(e) {
            e.preventDefault();
            console.log('체크인 폼 제출 이벤트 - AJAX 처리 시작');
            
            // 필수 동의 체크
            const agreeCheckbox = this.querySelector('input[name="agreeInfo"]');
            if (agreeCheckbox && !agreeCheckbox.checked) {
                alert('[필수] 항목에 동의해주셔야 체크인이 가능합니다.');
                return;
            }
            
            // 에러 메시지 숨기기
            const errorBox = document.getElementById('checkinErrorBox');
            if (errorBox) errorBox.classList.add('hidden');
            
            // AJAX 요청
            const formData = new FormData(this);
            console.log('체크인 FormData 생성 완료, AJAX 요청 시작');
            
            fetch(window.contextPath + '/checkin/lookup.htm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                    'Accept': 'application/json'
                },
                body: new URLSearchParams(formData)
            })
            .then(response => {
                console.log('체크인 응답 받음:', response);
                return response.json();
            })
            .then(data => {
                console.log('체크인 JSON 데이터:', data);
                if (data.success) {
                    const redirectUrl = window.contextPath + '/' + data.redirectUrl;
                    console.log('체크인 리다이렉트 URL:', redirectUrl);
                    window.location.href = redirectUrl;
                } else {
                    const errorMessageElement = document.getElementById('checkinErrorMessage');
                    if (errorBox && errorMessageElement) {
                        errorMessageElement.textContent = data.error || '알 수 없는 오류가 발생했습니다.';
                        errorBox.classList.remove('hidden');
                    }
                }
            })
            .catch(error => {
                console.error('체크인 AJAX 오류:', error);
                const errorMessageElement = document.getElementById('checkinErrorMessage');
                if (errorBox && errorMessageElement) {
                    errorMessageElement.textContent = '체크인 조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
                    errorBox.classList.remove('hidden');
                }
            });
        });
    }

    // 페이지 캐시 관련 이벤트 (메인 페이지에만 존재)
    const checkinTab = document.querySelector('#checkin');
    if (checkinTab) {
        window.addEventListener('pageshow', function(event) {
        // event.persisted 속성은 페이지가 bfcache에서 로드되었을 때 true가 됩니다.
        if (event.persisted) {
            // '예약 조회' 탭의 비회원 조회 폼을 찾습니다.
            const lookupForm = document.querySelector('#checkin .checkin-form');
            if (lookupForm) {
                // reset() 대신 각 필드를 직접 찾아 값을 비웁니다.
                const bookingIdInput = lookupForm.querySelector('input[name="bookingId"]');
                const departureDateInput = lookupForm.querySelector('input[name="departureDate"]');
                const lastNameInput = lookupForm.querySelector('input[name="lastName"]');
                const firstNameInput = lookupForm.querySelector('input[name="firstName"]');
                const checkbox = lookupForm.querySelector('input[type="checkbox"]');

                if (bookingIdInput) bookingIdInput.value = '';
                if (departureDateInput) departureDateInput.value = '';
                if (lastNameInput) lastNameInput.value = '';
                if (firstNameInput) firstNameInput.value = '';
                if (checkbox) checkbox.checked = false; // 체크박스도 해제
            }
            
            // bfcache에 남아있을 수 있는 조회 실패 오류 메시지를 찾아 제거합니다.
            const lookupError = document.querySelector('#checkin .booking-error');
            if (lookupError) {
                lookupError.remove();
            }
        }
    });
    }

    // 로그인한 상태에서 예약 상세 페이지로 이동하는 함수
    window.goToReservationDetail = function(bookingId) {
        const url = window.contextPath + '/reservation/detail.htm?bookingId=' + bookingId;
        window.location.href = url;
    };

    // 로그인한 상태에서 체크인 상세 페이지로 이동하는 함수
    window.goToCheckinDetail = function(bookingId) {
        const url = window.contextPath + '/checkin/lookup.htm?bookingId=' + bookingId;
        window.location.href = url;
    };

    // 로그인 상태 더보기(예약조회) 버튼 AJAX
    const loginLookupBtn = document.getElementById('loginLookupBtn');
    if (loginLookupBtn) {
        loginLookupBtn.addEventListener('click', function() {
            const bookingId = this.getAttribute('data-bookingid');
            const departureDate = this.getAttribute('data-departuredate');
            const lastName = this.getAttribute('data-lastname');
            const firstName = this.getAttribute('data-firstname');
            const errorBox = document.getElementById('bookingErrorBox');
            if (errorBox) errorBox.classList.add('hidden');
            fetch(window.contextPath + '/reservation/lookup.htm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                    'Accept': 'application/json'
                },
                body: new URLSearchParams({
                    bookingId,
                    departureDate,
                    lastName,
                    firstName,
                    agreeInfo: true
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    window.location.href = window.contextPath + '/' + data.redirectUrl;
                } else {
                    const errorMessageElement = document.getElementById('bookingErrorMessage');
                    if (errorBox && errorMessageElement) {
                        errorMessageElement.textContent = data.error || '알 수 없는 오류가 발생했습니다.';
                        errorBox.classList.remove('hidden');
                    }
                }
            })
            .catch(() => {
                const errorMessageElement = document.getElementById('bookingErrorMessage');
                if (errorBox && errorMessageElement) {
                    errorMessageElement.textContent = '조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
                    errorBox.classList.remove('hidden');
                }
            });
        });
    }

    // 로그인 상태 체크인 버튼 AJAX
    const loginCheckinBtn = document.getElementById('loginCheckinBtn');
    if (loginCheckinBtn) {
        loginCheckinBtn.addEventListener('click', function() {
            const bookingId = this.getAttribute('data-bookingid');
            const departureDate = this.getAttribute('data-departuredate');
            const lastName = this.getAttribute('data-lastname');
            const firstName = this.getAttribute('data-firstname');
            const errorBox = document.getElementById('checkinErrorBox');
            if (errorBox) errorBox.classList.add('hidden');
            fetch(window.contextPath + '/checkin/lookup.htm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                    'Accept': 'application/json'
                },
                body: new URLSearchParams({
                    bookingId,
                    departureDate,
                    lastName,
                    firstName,
                    agreeInfo: true
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    window.location.href = window.contextPath + '/' + data.redirectUrl;
                } else {
                    const errorMessageElement = document.getElementById('checkinErrorMessage');
                    if (errorBox && errorMessageElement) {
                        errorMessageElement.textContent = data.error || '알 수 없는 오류가 발생했습니다.';
                        errorBox.classList.remove('hidden');
                    }
                }
            })
            .catch(() => {
                const errorMessageElement = document.getElementById('checkinErrorMessage');
                if (errorBox && errorMessageElement) {
                    errorMessageElement.textContent = '체크인 조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
                    errorBox.classList.remove('hidden');
                }
            });
        });
    }

}); 