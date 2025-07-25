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

// ========== 출발지/도착지 검색 기능 ==========

// 출발지/도착지 검색 기능
const departureDiv = document.querySelector('.airport-input.departure');
const arrivalDiv = document.querySelector('.airport-input.arrival');
const departureDropdown = document.getElementById('departure-dropdown');
const arrivalDropdown = document.getElementById('arrival-dropdown');
const departureSearch = document.getElementById('departure-search');
const arrivalSearch = document.getElementById('arrival-search');
const departureResults = document.getElementById('departure-results');
const arrivalResults = document.getElementById('arrival-results');
const departureClose = document.getElementById('departure-close');
const arrivalClose = document.getElementById('arrival-close');



// 서버에서 공항 검색하는 함수
function searchAirports(keyword, resultsContainer) {
    console.log('공항 검색:', keyword);
    
    if (!keyword || keyword.length === 0) {
        resultsContainer.innerHTML = '';
        return;
    }
    
    // 여러 API 경로 시도
    const possibleUrls = [
        `${window.contextPath}/api/airport?keyword=${encodeURIComponent(keyword)}`,
        `/api/airport?keyword=${encodeURIComponent(keyword)}`,
        `${window.contextPath}/airport/search?keyword=${encodeURIComponent(keyword)}`,
        `${window.contextPath}/autocomplete.do?keyword=${encodeURIComponent(keyword)}`
    ];
    
    const url = possibleUrls[0]; // 먼저 첫 번째 시도
    console.log('API 호출 URL:', url);
    console.log('contextPath:', window.contextPath);
    
    fetch(url)
        .then(response => {
            console.log('응답 상태:', response.status);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.text(); // 먼저 텍스트로 받아서 확인
        })
        .then(text => {
            console.log('서버 응답 내용 (처음 200자):', text.substring(0, 200));
            
            // JSON인지 확인
            if (text.trim().startsWith('<')) {
                console.error('서버에서 HTML을 반환했습니다. API 경로가 잘못되었을 수 있습니다.');
                throw new Error('서버에서 HTML을 반환했습니다. API 경로를 확인하세요.');
            }
            
            const airports = JSON.parse(text);
            console.log('파싱된 공항 데이터:', airports);
            console.log('검색 결과:', airports.length + '개');
            
            resultsContainer.innerHTML = '';
            
            if (airports.length === 0) {
                const noResult = document.createElement('div');
                noResult.className = 'dropdown-item no-result';
                noResult.textContent = '검색 결과가 없습니다.';
                resultsContainer.appendChild(noResult);
                return;
            }
            
            airports.forEach(airport => {
                const item = document.createElement('div');
                item.className = 'dropdown-item';
                item.innerHTML = `
                    <div class="airport-item">
                        <div class="airport-code">${airport.airportId}</div>
                        <div class="airport-info">
                            <div class="airport-name">${airport.airportName}</div>
                            <div class="airport-full-name">${airport.cityName || airport.airportName}</div>
                        </div>
                    </div>
                `;
                
                item.addEventListener('click', function() {
                    const container = resultsContainer.closest('.airport-dropdown');
                    const isDeparture = container && container.id.includes('departure');
                    
                    if (isDeparture) {
                        const departureCode = document.querySelector('.departure .airport-code');
                        const departureName = document.querySelector('.departure .airport-name');
                        if (departureCode) departureCode.textContent = airport.airportId;
                        if (departureName) departureName.textContent = airport.airportName;
                        if (departureDropdown) departureDropdown.style.display = 'none';
                    } else {
                        const arrivalCode = document.querySelector('.arrival .airport-code');
                        const arrivalName = document.querySelector('.arrival .airport-name');
                        if (arrivalCode) arrivalCode.textContent = airport.airportId;
                        if (arrivalName) arrivalName.textContent = airport.airportName;
                        if (arrivalDropdown) arrivalDropdown.style.display = 'none';
                    }
                    
                    resultsContainer.innerHTML = '';
                });
                
                resultsContainer.appendChild(item);
            });
        })
        .catch(error => {
            console.error('공항 검색 API 오류:', error);
            console.log('로컬 데이터로 fallback 시도');
            
            // 로컬 데이터로 fallback
            const localAirports = [
                { airportId: 'ICN', airportName: '서울/인천', cityName: '인천국제공항' },
                { airportId: 'GMP', airportName: '서울/김포', cityName: '김포국제공항' },
                { airportId: 'PUS', airportName: '부산', cityName: '김해국제공항' },
                { airportId: 'CJU', airportName: '제주', cityName: '제주국제공항' },
                { airportId: 'TAE', airportName: '대구', cityName: '대구국제공항' },
                { airportId: 'KWJ', airportName: '광주', cityName: '광주공항' },
                { airportId: 'NRT', airportName: '도쿄/나리타', cityName: '나리타국제공항' },
                { airportId: 'HND', airportName: '도쿄/하네다', cityName: '하네다공항' },
                { airportId: 'KIX', airportName: '오사카', cityName: '간사이국제공항' },
                { airportId: 'FUK', airportName: '후쿠오카', cityName: '후쿠오카공항' },
                { airportId: 'PEK', airportName: '베이징', cityName: '베이징수도국제공항' },
                { airportId: 'PVG', airportName: '상하이/푸동', cityName: '상하이푸동국제공항' },
                { airportId: 'HKG', airportName: '홍콩', cityName: '홍콩국제공항' },
                { airportId: 'BKK', airportName: '방콕', cityName: '수완나품국제공항' },
                { airportId: 'SIN', airportName: '싱가포르', cityName: '창이국제공항' },
                { airportId: 'LAX', airportName: '로스앤젤레스', cityName: '로스앤젤레스국제공항' },
                { airportId: 'JFK', airportName: '뉴욕/JFK', cityName: '존F케네디국제공항' },
                { airportId: 'LHR', airportName: '런던/히드로', cityName: '히드로공항' },
                { airportId: 'CDG', airportName: '파리', cityName: '샤를드골공항' },
                { airportId: 'AMS', airportName: '암스테르담', cityName: '스키폴공항' }
            ];
            
            const filtered = localAirports.filter(airport => 
                airport.airportId.toLowerCase().includes(keyword.toLowerCase()) ||
                airport.airportName.toLowerCase().includes(keyword.toLowerCase()) ||
                airport.cityName.toLowerCase().includes(keyword.toLowerCase())
            );
            
            console.log('로컬 검색 결과:', filtered.length + '개');
            
            resultsContainer.innerHTML = '';
            
            if (filtered.length === 0) {
                const noResult = document.createElement('div');
                noResult.className = 'dropdown-item no-result';
                noResult.textContent = '검색 결과가 없습니다.';
                resultsContainer.appendChild(noResult);
                return;
            }
            
            filtered.forEach(airport => {
                const item = document.createElement('div');
                item.className = 'dropdown-item';
                item.innerHTML = `
                    <div class="airport-item">
                        <div class="airport-code">${airport.airportId}</div>
                        <div class="airport-info">
                            <div class="airport-name">${airport.airportName}</div>
                            <div class="airport-full-name">${airport.cityName}</div>
                        </div>
                    </div>
                `;
                
                item.addEventListener('click', function() {
                    const container = resultsContainer.closest('.airport-dropdown');
                    const isDeparture = container && container.id.includes('departure');
                    
                    if (isDeparture) {
                        const departureCode = document.querySelector('.departure .airport-code');
                        const departureName = document.querySelector('.departure .airport-name');
                        if (departureCode) departureCode.textContent = airport.airportId;
                        if (departureName) departureName.textContent = airport.airportName;
                        if (departureDropdown) departureDropdown.style.display = 'none';
                    } else {
                        const arrivalCode = document.querySelector('.arrival .airport-code');
                        const arrivalName = document.querySelector('.arrival .airport-name');
                        if (arrivalCode) arrivalCode.textContent = airport.airportId;
                        if (arrivalName) arrivalName.textContent = airport.airportName;
                        if (arrivalDropdown) arrivalDropdown.style.display = 'none';
                    }
                    
                    resultsContainer.innerHTML = '';
                });
                
                resultsContainer.appendChild(item);
            });
        });
}

// 드롭다운 위치 설정 함수
function positionDropdown(triggerElement, dropdown) {
    if (!triggerElement || !dropdown) return;
    
    const rect = triggerElement.getBoundingClientRect();
    const dropdownWidth = 350;
    const dropdownHeight = 500;
    
    let top = rect.bottom + 5;
    let left = rect.left;
    
    // 화면 오른쪽 경계 체크
    if (left + dropdownWidth > window.innerWidth) {
        left = window.innerWidth - dropdownWidth - 10;
    }
    
    // 화면 왼쪽 경계 체크
    if (left < 10) {
        left = 10;
    }
    
    // 화면 하단 경계 체크 - 위쪽으로 표시
    if (top + dropdownHeight > window.innerHeight) {
        top = rect.top - dropdownHeight - 5;
        if (top < 10) {
            top = Math.max(10, (window.innerHeight - dropdownHeight) / 2);
        }
    }
    
    dropdown.style.top = top + 'px';
    dropdown.style.left = left + 'px';
    dropdown.style.zIndex = '2147483647';
    dropdown.style.position = 'fixed';
}

// 출발지 검색 이벤트
if (departureSearch) {
    departureSearch.addEventListener('input', function() {
        if (departureResults) {
            searchAirports(this.value, departureResults);
        }
    });

    // 검색창에 포커스될 때 테두리 색상 변경
    departureSearch.addEventListener('focus', function() {
        this.style.borderColor = '#0066cc';
    });
    departureSearch.addEventListener('blur', function() {
        this.style.borderColor = '#e0e0e0';
    });
}

// 도착지 검색 이벤트
if (arrivalSearch) {
    arrivalSearch.addEventListener('input', function() {
        if (arrivalResults) {
            searchAirports(this.value, arrivalResults);
        }
    });

    // 검색창에 포커스될 때 테두리 색상 변경
    arrivalSearch.addEventListener('focus', function() {
        this.style.borderColor = '#0066cc';
    });
    arrivalSearch.addEventListener('blur', function() {
        this.style.borderColor = '#e0e0e0';
    });
}

// X 버튼 클릭 이벤트
if (departureClose) {
    departureClose.addEventListener('click', function(e) {
        e.stopPropagation();
        if (departureDropdown) departureDropdown.style.display = 'none';
    });
}

if (arrivalClose) {
    arrivalClose.addEventListener('click', function(e) {
        e.stopPropagation();
        if (arrivalDropdown) arrivalDropdown.style.display = 'none';
    });
}

// 출발지 클릭 이벤트
if (departureDiv) {
    departureDiv.addEventListener('click', function(e) {
        if (arrivalDropdown) arrivalDropdown.style.display = 'none';
        if (departureDropdown && (departureDropdown.style.display === 'none' || departureDropdown.style.display === '')) {
            positionDropdown(departureDiv, departureDropdown);
            departureDropdown.style.display = 'block';
            if (departureSearch) {
                setTimeout(() => departureSearch.focus(), 100);
            }
        } else if (departureDropdown) {
            departureDropdown.style.display = 'none';
        }
        e.stopPropagation();
    });
}

// 도착지 클릭 이벤트
if (arrivalDiv) {
    arrivalDiv.addEventListener('click', function(e) {
        if (departureDropdown) departureDropdown.style.display = 'none';
        if (arrivalDropdown && (arrivalDropdown.style.display === 'none' || arrivalDropdown.style.display === '')) {
            positionDropdown(arrivalDiv, arrivalDropdown);
            arrivalDropdown.style.display = 'block';
            if (arrivalSearch) {
                setTimeout(() => arrivalSearch.focus(), 100);
            }
        } else if (arrivalDropdown) {
            arrivalDropdown.style.display = 'none';
        }
        e.stopPropagation();
    });
}

// 드롭다운 내부 클릭 시 이벤트 전파 방지
if (departureDropdown) {
    departureDropdown.addEventListener('click', function(e) {
        e.stopPropagation();
    });
}

if (arrivalDropdown) {
    arrivalDropdown.addEventListener('click', function(e) {
        e.stopPropagation();
    });
}

// 바깥 클릭 시 모든 드롭다운 닫기
document.addEventListener('click', function(e) {
    if (departureDiv && arrivalDiv && 
        !departureDiv.contains(e.target) && 
        !arrivalDiv.contains(e.target) &&
        !(departureDropdown && departureDropdown.contains(e.target)) &&
        !(arrivalDropdown && arrivalDropdown.contains(e.target))) {
        if (departureDropdown) departureDropdown.style.display = 'none';
        if (arrivalDropdown) arrivalDropdown.style.display = 'none';
    }
});

// 스크롤 시 드롭다운 위치 재조정
window.addEventListener('scroll', function() {
    if (departureDropdown && departureDropdown.style.display === 'block') {
        positionDropdown(departureDiv, departureDropdown);
    }
    if (arrivalDropdown && arrivalDropdown.style.display === 'block') {
        positionDropdown(arrivalDiv, arrivalDropdown);
    }
});

// 윈도우 리사이즈 시 드롭다운 위치 재조정
window.addEventListener('resize', function() {
    if (departureDropdown && departureDropdown.style.display === 'block') {
        positionDropdown(departureDiv, departureDropdown);
    }
    if (arrivalDropdown && arrivalDropdown.style.display === 'block') {
        positionDropdown(arrivalDiv, arrivalDropdown);
    }
});

console.log('출발지/도착지 검색 기능 초기화 완료');

// 항공편 검색 폼 submit 이벤트 처리
const searchForm = document.getElementById('searchForm');
if (searchForm) {
    searchForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        console.log('항공편 검색 폼 제출');
        
        // 1. 출발지/도착지 코드 가져오기
        const departureCode = document.querySelector('.departure .airport-code').textContent || 'CJU';
        const arrivalCode = document.querySelector('.arrival .airport-code').textContent || 'GMP';
        
        // 2. 날짜 값 가져오기 (임시로 기본값 사용)
        const departureDate = '2025-07-15';
        const returnDate = '2025-07-16';
        
        // 3. 탑승객 정보 (임시로 기본값 사용)
        const passengers = '성인 1명';
        
        // 4. 좌석 등급 (임시로 기본값 사용)
        const seatClass = '일반석';
        
        // 5. 여행 타입 (임시로 기본값 사용)
        const tripType = 'round';
        
        console.log('검색 조건:', {
            departure: departureCode,
            arrival: arrivalCode,
            departureDate: departureDate,
            returnDate: returnDate,
            passengers: passengers,
            seatClass: seatClass,
            tripType: tripType
        });
        
        // 6. Hidden input 필드에 값 설정
        document.getElementById('departureInput').value = departureCode;
        document.getElementById('arrivalInput').value = arrivalCode;
        document.getElementById('departureDateInput').value = departureDate;
        document.getElementById('returnDateInput').value = returnDate;
        document.getElementById('passengersInput').value = passengers;
        document.getElementById('seatClassInput').value = seatClass;
        document.getElementById('tripTypeInput').value = tripType;
        
        // 7. 폼 제출
        this.submit();
    });
    
    console.log('항공편 검색 폼 이벤트 리스너 설정 완료');
} else {
    console.log('검색 폼을 찾을 수 없습니다');
} 