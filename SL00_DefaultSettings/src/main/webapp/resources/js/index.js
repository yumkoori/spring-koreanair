document.addEventListener('DOMContentLoaded', function() {
    // 탭 기능 구현 (메인 페이지에만 존재)

    console.log('=== INDEX.JS 로드됨 ===');
    console.log('DOMContentLoaded 이벤트 발생');
    

    
    // ===== 탑승객 선택 기능을 최우선으로 실행 =====
    console.log('=== 탑승객 선택 기능 초기화 시작 ===');
    
    // 즉시 실행되는 탑승객 기능
    function initPassengerSelector() {
        console.log('initPassengerSelector 함수 실행');
        
        const passengerSelector = document.querySelector('.passenger-selector');
        const passengerDisplay = document.querySelector('.passenger-display');
        const passengersDropdown = document.querySelector('.passengers-dropdown');
        
        console.log('찾은 요소들:');
        console.log('- passengerSelector:', passengerSelector);
        console.log('- passengerDisplay:', passengerDisplay);
        console.log('- passengersDropdown:', passengersDropdown);
        
        if (passengerSelector) {
            console.log('탑승객 선택기 HTML:', passengerSelector.outerHTML.substring(0, 100) + '...');
            

             
             // 직접 클릭 이벤트 추가
             passengerSelector.onclick = function(e) {
                 console.log('=== 탑승객 선택기 클릭됨! ===');
                 e.preventDefault();
                 e.stopPropagation();
                 

                 
                 // 기존 커스텀 드롭다운이 있으면 제거
                 const existingCustomDropdown = document.getElementById('custom-passenger-dropdown');
                 if (existingCustomDropdown) {
                     existingCustomDropdown.remove();
                     console.log('기존 커스텀 드롭다운 제거됨');
                     return;
                 }
                 
                 // 새로운 드롭다운 생성
                 const customDropdown = document.createElement('div');
                 customDropdown.id = 'custom-passenger-dropdown';
                 customDropdown.innerHTML = `
                     <div style="padding: 20px; background: #fff;">
                         <div style="font-size: 16px; font-weight: 600; color: #333; margin-bottom: 20px;">탑승객 선택</div>
                         
                         <div style="margin-bottom: 20px;">
                             <div style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0;">
                                 <div>
                                     <div style="font-weight: 600; color: #333; font-size: 14px; margin-bottom: 4px;">성인</div>
                                     <div style="font-size: 12px; color: #666;">만 12세 이상</div>
                                 </div>
                                 <div style="display: flex; align-items: center; gap: 16px;">
                                     <button class="custom-count-btn decrease" data-type="adult" style="width: 36px; height: 36px; border: 1px solid #e1e5e9; background: #fff; border-radius: 6px; cursor: pointer; font-size: 16px; color: #333; display: flex; align-items: center; justify-content: center; transition: all 0.2s;">−</button>
                                     <span class="custom-count adult-count" style="min-width: 24px; text-align: center; font-weight: 600; font-size: 16px; color: #333;">1</span>
                                     <button class="custom-count-btn increase" data-type="adult" style="width: 36px; height: 36px; border: 1px solid #e1e5e9; background: #fff; border-radius: 6px; cursor: pointer; font-size: 16px; color: #333; display: flex; align-items: center; justify-content: center; transition: all 0.2s;">+</button>
                                 </div>
                             </div>
                             
                             <div style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0; border-top: 1px solid #f5f5f5;">
                                 <div>
                                     <div style="font-weight: 600; color: #333; font-size: 14px; margin-bottom: 4px;">소아</div>
                                     <div style="font-size: 12px; color: #666;">만 2-11세</div>
                                 </div>
                                 <div style="display: flex; align-items: center; gap: 16px;">
                                     <button class="custom-count-btn decrease" data-type="child" style="width: 36px; height: 36px; border: 1px solid #e1e5e9; background: #fff; border-radius: 6px; cursor: pointer; font-size: 16px; color: #333; display: flex; align-items: center; justify-content: center; transition: all 0.2s;">−</button>
                                     <span class="custom-count child-count" style="min-width: 24px; text-align: center; font-weight: 600; font-size: 16px; color: #333;">0</span>
                                     <button class="custom-count-btn increase" data-type="child" style="width: 36px; height: 36px; border: 1px solid #e1e5e9; background: #fff; border-radius: 6px; cursor: pointer; font-size: 16px; color: #333; display: flex; align-items: center; justify-content: center; transition: all 0.2s;">+</button>
                                 </div>
                             </div>
                             
                             <div style="display: flex; justify-content: space-between; align-items: center; padding: 16px 0; border-top: 1px solid #f5f5f5;">
                                 <div>
                                     <div style="font-weight: 600; color: #333; font-size: 14px; margin-bottom: 4px;">유아</div>
                                     <div style="font-size: 12px; color: #666;">만 2세 미만</div>
                                 </div>
                                 <div style="display: flex; align-items: center; gap: 16px;">
                                     <button class="custom-count-btn decrease" data-type="infant" style="width: 36px; height: 36px; border: 1px solid #e1e5e9; background: #fff; border-radius: 6px; cursor: pointer; font-size: 16px; color: #333; display: flex; align-items: center; justify-content: center; transition: all 0.2s;">−</button>
                                     <span class="custom-count infant-count" style="min-width: 24px; text-align: center; font-weight: 600; font-size: 16px; color: #333;">0</span>
                                     <button class="custom-count-btn increase" data-type="infant" style="width: 36px; height: 36px; border: 1px solid #e1e5e9; background: #fff; border-radius: 6px; cursor: pointer; font-size: 16px; color: #333; display: flex; align-items: center; justify-content: center; transition: all 0.2s;">+</button>
                                 </div>
                             </div>
                         </div>
                         
                         <div style="display: flex; gap: 12px; margin-top: 24px;">
                             <button id="custom-cancel-btn" style="flex: 1; padding: 12px 16px; border: 1px solid #e1e5e9; background: #fff; border-radius: 6px; cursor: pointer; font-size: 14px; font-weight: 500; color: #333; transition: all 0.2s;">취소</button>
                             <button id="custom-apply-btn" style="flex: 1; padding: 12px 16px; border: none; background: #0066cc; color: white; border-radius: 6px; cursor: pointer; font-size: 14px; font-weight: 500; transition: all 0.2s;">확인</button>
                         </div>
                     </div>
                 `;
                 
                 // 위치와 스타일 설정
                 const rect = passengerSelector.getBoundingClientRect();
                 const viewportWidth = window.innerWidth;
                 const viewportHeight = window.innerHeight;
                 
                 // 드롭다운이 화면을 벗어나지 않도록 위치 조정
                 let dropdownTop = rect.bottom + 4;
                 let dropdownLeft = rect.left;
                 let dropdownWidth = Math.min(rect.width, 350); // 최대 350px로 제한
                 
                 // 오른쪽으로 벗어나는 경우 조정
                 if (dropdownLeft + dropdownWidth > viewportWidth - 20) {
                     dropdownLeft = viewportWidth - dropdownWidth - 20;
                 }
                 
                 // 아래로 벗어나는 경우 위쪽에 표시
                 if (dropdownTop + 300 > viewportHeight) {
                     dropdownTop = rect.top - 304; // 드롭다운 높이만큼 위로
                 }
                 
                 customDropdown.style.cssText = `
                     position: fixed !important;
                     top: ${dropdownTop}px !important;
                     left: ${dropdownLeft}px !important;
                     width: ${dropdownWidth}px !important;
                     max-width: 350px !important;
                     z-index: 999999 !important;
                     background: #ffffff !important;
                     border: 1px solid #ddd !important;
                     border-radius: 8px !important;
                     box-shadow: 0 4px 12px rgba(0,0,0,0.1) !important;
                     font-family: 'Nanum Gothic', sans-serif !important;
                     display: block !important;
                     visibility: visible !important;
                     opacity: 1 !important;
                     max-height: 300px !important;
                     overflow: visible !important;
                 `;
                 
                 // body에 추가
                 document.body.appendChild(customDropdown);
                 console.log('커스텀 드롭다운 생성됨');
                 
                 // 버튼 이벤트 추가
                 const countBtns = customDropdown.querySelectorAll('.custom-count-btn');
                 countBtns.forEach(btn => {
                     btn.onclick = function(e) {
                         e.stopPropagation();
                         const type = this.dataset.type;
                         const isIncrease = this.classList.contains('increase');
                         const countSpan = customDropdown.querySelector(`.${type}-count`);
                         let currentCount = parseInt(countSpan.textContent);
                         
                         if (isIncrease) {
                             if (currentCount < 9) {
                                 currentCount++;
                             }
                         } else {
                             if (currentCount > 0) {
                                 currentCount--;
                             }
                         }
                         
                         countSpan.textContent = currentCount;
                         
                         // 전역 변수 업데이트
                         if (type === 'adult') adultCount = currentCount;
                         else if (type === 'child') childCount = currentCount;
                         else if (type === 'infant') infantCount = currentCount;
                     };
                 });
                 
                 // 취소 버튼 이벤트
                 const cancelBtn = customDropdown.querySelector('#custom-cancel-btn');
                 cancelBtn.onclick = function() {
                     customDropdown.remove();
                     console.log('드롭다운 취소 및 닫힘');
                 };
                 
                 // 적용 버튼 이벤트
                 const applyBtn = customDropdown.querySelector('#custom-apply-btn');
                 applyBtn.onclick = function() {
                     updatePassengerDisplay();
                     customDropdown.remove();
                     console.log('드롭다운 적용 및 닫힘');
                 };
                 
                 // 외부 클릭 시 닫기
                 document.addEventListener('click', function closeDropdown(e) {
                     if (!customDropdown.contains(e.target) && !passengerSelector.contains(e.target)) {
                         customDropdown.remove();
                         document.removeEventListener('click', closeDropdown);
                         console.log('외부 클릭으로 드롭다운 닫힘');
                     }
                 });
             };
            
            console.log('탑승객 선택기에 onclick 이벤트 추가됨');
        } else {
            console.log('탑승객 선택기를 찾을 수 없음');
        }
    }
    
    // 즉시 실행
    initPassengerSelector();
    
    // 1초 후 재시도
    setTimeout(initPassengerSelector, 1000);
    
    // 탭 기능 구현

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
    const departureAllRegions = document.getElementById('departure-all-regions');
    const arrivalAllRegions = document.getElementById('arrival-all-regions');



    // 출발지 검색 이벤트
    if (departureSearch) {

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
            searchAirports(this.value, arrivalResults);
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
            departureDropdown.style.display = 'none';
        });
    }
    
    if (arrivalClose) {
        arrivalClose.addEventListener('click', function(e) {
            e.stopPropagation();
            arrivalDropdown.style.display = 'none';
        });
    }



    // 드롭다운 위치 설정 함수
    function positionDropdown(triggerElement, dropdown) {
        // CSS의 모든 transform과 위치 속성 제거
        dropdown.style.removeProperty('transform');
        dropdown.style.removeProperty('top');
        dropdown.style.removeProperty('left');
        
        const rect = triggerElement.getBoundingClientRect();
        const dropdownWidth = 350;
        const dropdownHeight = 300;
        
        let top = rect.bottom + 5;
        let left = rect.left;
        
        console.log('위치 계산:', { 
            triggerElement: triggerElement.className,
            triggerRect: rect,
            windowWidth: window.innerWidth, 
            windowHeight: window.innerHeight,
            initialTop: top,
            initialLeft: left
        });
        
        // 드롭다운을 더 왼쪽으로 이동
        left = left - 250; // 250px 왼쪽으로 이동
        
        // 오른쪽 경계 체크
        if (left + dropdownWidth > window.innerWidth - 10) {
            left = window.innerWidth - dropdownWidth - 10;
            console.log('오른쪽 경계 조정:', left);
        }
        
        // 왼쪽 경계 체크
        if (left < 10) {
            left = 10;
            console.log('왼쪽 경계 조정:', left);
        }
        
        // 항상 위쪽에 배치하도록 변경
        top = rect.top - dropdownHeight - 5;
        console.log('위쪽으로 배치:', top);
        
        // 위쪽으로 배치했을 때 화면 상단을 벗어나면 아래쪽으로 배치
        if (top < 10) {
            top = rect.bottom + 5;
            console.log('화면 상단 벗어남, 아래쪽으로 배치:', top);
        }
        
        // 최종 위치가 음수가 되지 않도록 보장
        if (top < 0) top = 10;
        if (left < 0) left = 10;
        
        // 스타일 설정
        dropdown.style.setProperty('top', top + 'px', 'important');
        dropdown.style.setProperty('left', left + 'px', 'important');
        dropdown.style.setProperty('position', 'fixed', 'important');
        dropdown.style.setProperty('z-index', '2147483647', 'important');
        dropdown.style.setProperty('width', dropdownWidth + 'px', 'important');
        
        console.log('최종 위치:', { top, left, width: dropdownWidth, position: 'fixed' });
    }

    // 출발지 클릭 이벤트
    if (departureDiv) {
        departureDiv.addEventListener('click', function(e) {
            console.log('출발지 클릭됨');
            if (arrivalDropdown) arrivalDropdown.style.display = 'none';
            if (departureDropdown.style.display === 'none' || departureDropdown.style.display === '') {
                // 드롭다운을 먼저 표시
                departureDropdown.style.display = 'block';
                // 그 다음 위치 설정
                setTimeout(() => {
                    positionDropdown(departureDiv, departureDropdown);
                    if (departureSearch) {
                        departureSearch.focus();
                    }
                }, 10);
            } else {
                departureDropdown.style.display = 'none';
            }
            e.stopPropagation();
        });
    }

    // 도착지 클릭 이벤트
    if (arrivalDiv) {
        arrivalDiv.addEventListener('click', function(e) {
            console.log('도착지 클릭됨');
            if (departureDropdown) departureDropdown.style.display = 'none';
            if (arrivalDropdown.style.display === 'none' || arrivalDropdown.style.display === '') {
                // 드롭다운을 먼저 표시
                arrivalDropdown.style.display = 'block';
                // 그 다음 위치 설정
                setTimeout(() => {
                    positionDropdown(arrivalDiv, arrivalDropdown);
                    if (arrivalSearch) {
                        arrivalSearch.focus();
                    }
                }, 10);
            } else {
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
            !departureDropdown.contains(e.target) &&
            !arrivalDropdown.contains(e.target)) {
            if (departureDropdown) departureDropdown.style.display = 'none';
            if (arrivalDropdown) arrivalDropdown.style.display = 'none';
        }
    });

    // 달력 관련 변수들
    let currentDate = new Date();
    let selectedStartDate = null;
    let selectedEndDate = null;
    let isSelectingRange = false;
    let currentTripType = 'round'; // 기본값: 왕복

    // 달력 요소들
    const calendarPopup = document.getElementById('calendar-popup');
    const calendarOverlay = document.getElementById('calendar-overlay');
    const datePickerTrigger = document.getElementById('date-picker-trigger');
    const dateDisplay = document.getElementById('date-display');
    const dateLabel = document.getElementById('date-label');
    const calendarMonthYear = document.getElementById('calendar-month-year');
    const calendarDays = document.getElementById('calendar-days');
    const prevMonthBtn = document.getElementById('prev-month');
    const nextMonthBtn = document.getElementById('next-month');
    const calendarClearBtn = document.querySelector('.calendar-clear');
    const calendarApplyBtn = document.querySelector('.calendar-apply');

    // 날짜 포맷 함수
    function formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // 날짜 표시 업데이트
    function updateDateDisplay() {
        if (currentTripType === 'oneway') {
            if (selectedStartDate) {
                dateDisplay.value = formatDate(selectedStartDate);
            } else {
                dateDisplay.value = '출발일 선택';
            }
        } else {
            if (selectedStartDate && selectedEndDate) {
                dateDisplay.value = `${formatDate(selectedStartDate)} ~ ${formatDate(selectedEndDate)}`;
            } else if (selectedStartDate) {
                dateDisplay.value = `${formatDate(selectedStartDate)} ~ 도착일을 선택하세요`;
            } else {
                dateDisplay.value = '출발일 ~ 도착일 선택';
            }
        }
    }

    // 달력 생성
    function generateCalendar(year, month) {
        const firstDay = new Date(year, month, 1);
        const lastDay = new Date(year, month + 1, 0);
        const startDate = new Date(firstDay);
        startDate.setDate(startDate.getDate() - firstDay.getDay());
        
        const today = new Date();
        calendarDays.innerHTML = '';
        
        for (let i = 0; i < 42; i++) {
            const date = new Date(startDate);
            date.setDate(startDate.getDate() + i);
            
            const dayElement = document.createElement('div');
            dayElement.className = 'calendar-day';
            dayElement.textContent = date.getDate();
            dayElement.dataset.date = formatDate(date);
            
            // 다른 달의 날짜
            if (date.getMonth() !== month) {
                dayElement.classList.add('other-month');
            }
            
            // 오늘 날짜
            if (date.toDateString() === today.toDateString()) {
                dayElement.classList.add('today');
            }
            
            // 과거 날짜 비활성화
            if (date < today.setHours(0, 0, 0, 0)) {
                dayElement.style.color = '#ccc';
                dayElement.style.cursor = 'not-allowed';
            } else {
                // 선택된 날짜 스타일링
                if (selectedStartDate && date.toDateString() === selectedStartDate.toDateString()) {
                    dayElement.classList.add('start-date');
                }
                if (selectedEndDate && date.toDateString() === selectedEndDate.toDateString()) {
                    dayElement.classList.add('end-date');
                }
                
                // 범위 내 날짜 (왕복인 경우)
                if (currentTripType === 'round' && selectedStartDate && selectedEndDate) {
                    if (date > selectedStartDate && date < selectedEndDate) {
                        dayElement.classList.add('in-range');
                    }
                }
                
                // 클릭 이벤트
                dayElement.addEventListener('click', function() {
                    selectDate(new Date(date));
                });
            }
            
            calendarDays.appendChild(dayElement);
        }
    }

    // 날짜 선택 처리
    function selectDate(date) {
        if (currentTripType === 'oneway') {
            selectedStartDate = date;
            selectedEndDate = null;
            // 편도인 경우 날짜 선택 후 달력 자동 닫기
            setTimeout(() => {
                if (calendarPopup.classList.contains('show')) {
                    toggleCalendar();
                }
            }, 300);
        } else {
            if (!selectedStartDate || (selectedStartDate && selectedEndDate)) {
                // 첫 번째 날짜 선택 또는 재선택
                selectedStartDate = date;
                selectedEndDate = null;
                isSelectingRange = true;
                // 왕복인 경우 첫 번째 날짜 선택 후에는 달력을 열어둠
            } else if (selectedStartDate && !selectedEndDate) {
                // 두 번째 날짜 선택
                if (date >= selectedStartDate) {
                    selectedEndDate = date;
                } else {
                    selectedEndDate = selectedStartDate;
                    selectedStartDate = date;
                }
                isSelectingRange = false;
                // 왕복인 경우 두 번째 날짜 선택 후 달력 자동 닫기
                setTimeout(() => {
                    if (calendarPopup.classList.contains('show')) {
                        toggleCalendar();
                    }
                }, 300);
            }
        }
        
        updateDateDisplay();
        generateCalendar(currentDate.getFullYear(), currentDate.getMonth());
    }

    // 달력 위치 계산
    function positionCalendar() {
        if (!datePickerTrigger || !calendarPopup) return;
        
        const triggerRect = datePickerTrigger.getBoundingClientRect();
        const calendarHeight = 400; // 대략적인 달력 높이
        const viewportHeight = window.innerHeight;
        const viewportWidth = window.innerWidth;
        
        // 기본적으로 트리거 아래에 표시
        let top = triggerRect.bottom + 5;
        let left = triggerRect.left;
        let width = Math.max(320, triggerRect.width);
        
        // 화면 아래로 넘어가는 경우 위에 표시
        if (top + calendarHeight > viewportHeight) {
            top = triggerRect.top - calendarHeight - 5;
        }
        
        // 화면 오른쪽으로 넘어가는 경우 조정
        if (left + width > viewportWidth) {
            left = viewportWidth - width - 10;
        }
        
        // 화면 왼쪽으로 넘어가는 경우 조정
        if (left < 10) {
            left = 10;
        }
        
        calendarPopup.style.top = top + 'px';
        calendarPopup.style.left = left + 'px';
        calendarPopup.style.width = width + 'px';
        calendarPopup.style.right = 'auto';
    }

    // 달력 표시/숨김
    function toggleCalendar() {
        const isShowing = calendarPopup.classList.contains('show');
        
        if (!isShowing) {
            // 달력 열기
            // 오버레이와 달력을 body에 직접 추가
            document.body.appendChild(calendarOverlay);
            document.body.appendChild(calendarPopup);
            
            // 오버레이 표시
            calendarOverlay.classList.add('show');
            calendarPopup.classList.add('show');
            
            // 강제로 최상단에 표시
            calendarOverlay.style.zIndex = '999999998';
            calendarOverlay.style.position = 'fixed';
            
            calendarPopup.style.zIndex = '999999999';
            calendarPopup.style.position = 'fixed';
            calendarPopup.style.transform = 'translateZ(0)';
            calendarPopup.style.willChange = 'transform';
            calendarPopup.style.isolation = 'isolate';
            calendarPopup.style.backfaceVisibility = 'hidden';
            calendarPopup.style.background = 'white';
            calendarPopup.style.opacity = '1';
            calendarPopup.style.visibility = 'visible';
            calendarPopup.style.pointerEvents = 'auto';
            
            positionCalendar();
            generateCalendar(currentDate.getFullYear(), currentDate.getMonth());
            updateCalendarHeader();
        } else {
            // 달력 닫기
            calendarOverlay.classList.remove('show');
            calendarPopup.classList.remove('show');
            
            // 원래 위치로 되돌림
            const dateInput = document.querySelector('.date-input');
            if (dateInput) {
                if (calendarOverlay.parentNode === document.body) {
                    dateInput.appendChild(calendarOverlay);
                }
                if (calendarPopup.parentNode === document.body) {
                    dateInput.appendChild(calendarPopup);
                }
            }
        }
    }

    // 달력 헤더 업데이트
    function updateCalendarHeader() {
        const months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];
        calendarMonthYear.textContent = `${currentDate.getFullYear()}년 ${months[currentDate.getMonth()]}`;
    }

    // 왕복/편도 버튼 클릭 이벤트
    const tripTypeButtons = document.querySelectorAll('.trip-type-btn');
    
    if (tripTypeButtons.length > 0) {
        tripTypeButtons.forEach(button => {
            button.addEventListener('click', function() {
                // 모든 버튼에서 active 클래스 제거
                tripTypeButtons.forEach(btn => btn.classList.remove('active'));
                
                // 클릭된 버튼에 active 클래스 추가
                this.classList.add('active');
                
                currentTripType = this.getAttribute('data-type');
                
                // 라벨 업데이트
                if (currentTripType === 'oneway') {
                    dateLabel.textContent = '출발일';
                    selectedEndDate = null; // 편도로 변경 시 도착일 초기화
                } else if (currentTripType === 'round') {
                    dateLabel.textContent = '출발일 ~ 도착일';
                } else if (currentTripType === 'multi') {
                    dateLabel.textContent = '출발일 ~ 도착일';
                }
                
                updateDateDisplay();
                if (calendarPopup.classList.contains('show')) {
                    generateCalendar(currentDate.getFullYear(), currentDate.getMonth());
                }
            });
        });
    }

    // 이벤트 리스너들
    if (datePickerTrigger) {
        datePickerTrigger.addEventListener('click', toggleCalendar);
    }

    if (prevMonthBtn) {
        prevMonthBtn.addEventListener('click', function() {
            currentDate.setMonth(currentDate.getMonth() - 1);
            generateCalendar(currentDate.getFullYear(), currentDate.getMonth());
            updateCalendarHeader();
        });
    }

    if (nextMonthBtn) {
        nextMonthBtn.addEventListener('click', function() {
            currentDate.setMonth(currentDate.getMonth() + 1);
            generateCalendar(currentDate.getFullYear(), currentDate.getMonth());
            updateCalendarHeader();
        });
    }

    if (calendarClearBtn) {
        calendarClearBtn.addEventListener('click', function() {
            selectedStartDate = null;
            selectedEndDate = null;
            updateDateDisplay();
            generateCalendar(currentDate.getFullYear(), currentDate.getMonth());
        });
    }

    if (calendarApplyBtn) {
        calendarApplyBtn.addEventListener('click', function() {
            if (calendarPopup.classList.contains('show')) {
                toggleCalendar();
            }
        });
    }

    // 달력 외부 클릭 시 닫기
    document.addEventListener('click', function(e) {
        if (!datePickerTrigger.contains(e.target) && !calendarPopup.contains(e.target)) {
            if (calendarPopup.classList.contains('show')) {
                // 왕복 모드에서는 두 날짜가 모두 선택되었을 때만 외부 클릭으로 닫기
                if (currentTripType === 'oneway' || (currentTripType === 'round' && selectedStartDate && selectedEndDate)) {
                    toggleCalendar();
                }
                // 편도 모드이거나 왕복 모드에서 두 날짜가 모두 선택된 경우에만 닫기
            }
        }
    });

    // 오버레이 클릭 시 달력 닫기
    if (calendarOverlay) {
        calendarOverlay.addEventListener('click', function() {
            if (calendarPopup.classList.contains('show')) {
                // 왕복 모드에서는 두 날짜가 모두 선택되었을 때만 오버레이 클릭으로 닫기
                if (currentTripType === 'oneway' || (currentTripType === 'round' && selectedStartDate && selectedEndDate)) {
                    toggleCalendar();
                }
            }
        });
    }

    // 스크롤 및 리사이즈 시 달력 위치 재조정
    window.addEventListener('scroll', function() {
        if (calendarPopup.classList.contains('show')) {
            positionCalendar();
        }
    });

    window.addEventListener('resize', function() {
        if (calendarPopup.classList.contains('show')) {
            positionCalendar();
        }
    });

    // 초기 날짜 설정
    selectedStartDate = new Date(2025, 6, 15); // 2025-07-15
    selectedEndDate = new Date(2025, 6, 16);   // 2025-07-16
    
    // 페이지 로드 시 초기 상태 설정
    const activeButton = document.querySelector('.trip-type-btn.active');
    if (activeButton) {
        const tripType = activeButton.getAttribute('data-type');
        currentTripType = tripType;
        if (tripType === 'oneway') {
            selectedEndDate = null; // 편도인 경우 도착일 제거
            if (dateLabel) {
                dateLabel.textContent = '출발일';
            }
        }
    }
    
    updateDateDisplay();

    // 항공편 검색 폼 submit 이벤트
    const searchForm = document.getElementById('searchForm');
    if (searchForm) {
        searchForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // 입력값 가져오기
            const departureCode = document.querySelector('.departure .airport-code').textContent || 'CJU';
            const arrivalCode = document.querySelector('.arrival .airport-code').textContent || 'GMP';
            
            // 날짜 값 가져오기 (기본값: 2025-07-15 ~ 2025-07-16)
            let departureDate = '2025-07-15';
            let returnDate = '2025-07-16';
            
            if (selectedStartDate) {
                const year = selectedStartDate.getFullYear();
                const month = String(selectedStartDate.getMonth() + 1).padStart(2, '0');
                const day = String(selectedStartDate.getDate()).padStart(2, '0');
                departureDate = `${year}-${month}-${day}`;
            }
            
            if (selectedEndDate && currentTripType !== 'oneway') {
                const year = selectedEndDate.getFullYear();
                const month = String(selectedEndDate.getMonth() + 1).padStart(2, '0');
                const day = String(selectedEndDate.getDate()).padStart(2, '0');
                returnDate = `${year}-${month}-${day}`;
            }
            
            // 탑승객 정보 가져오기 (기본값: 성인 1명)
            let passengers = '성인 1명';
            const passengerDisplayElement = document.querySelector('.passenger-display');
            if (passengerDisplayElement && passengerDisplayElement.textContent.trim()) {
                passengers = passengerDisplayElement.textContent.trim();
            }
            
            // 좌석 등급 (기본값: 일반석)
            const seatClass = '일반석';
            
            // 여행 타입 가져오기 (기본값: round)
            const tripType = currentTripType || 'round';
            
            // URL 생성
            const params = new URLSearchParams({
                departure: departureCode,
                arrival: arrivalCode,
                departureDate: departureDate,
                returnDate: returnDate,
                passengers: passengers,
                seatClass: seatClass,
                tripType: tripType
            });
            
			const url = `${contextPath}/api/search/flight?${params.toString()}`;
			window.location.href = url;
			
        });
    }

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

    // 기존 탑승객 선택 코드 (단순화됨)
    let adultCount = 1;
    let childCount = 0;
    let infantCount = 0;

    // 탑승객 수 업데이트 함수 (단순화됨)
    function updatePassengerDisplay() {
        const passengerDisplay = document.querySelector('.passenger-display');
        if (passengerDisplay) {
            let displayText = '';
            const parts = [];
            
            if (adultCount > 0) {
                parts.push(`성인 ${adultCount}명`);
            }
            if (childCount > 0) {
                parts.push(`소아 ${childCount}명`);
            }
            if (infantCount > 0) {
                parts.push(`유아 ${infantCount}명`);
            }
            
            displayText = parts.join(', ');
            passengerDisplay.textContent = displayText;
        }
    }

    // 초기 상태 설정
    updatePassengerDisplay();
}); 


document.querySelector('#departure-search').addEventListener('input', function () {
    const keyword = this.value.trim();
    if (keyword.length === 0) return;
	fetch(`${window.contextPath}/api/airport?keyword=${encodeURIComponent(keyword)}`)
	    .then(response => response.json())
        .then(data => {
            const resultsContainer = document.getElementById('departure-results');
            resultsContainer.innerHTML = '';
            data.forEach(item => {
                const div = document.createElement('div');
                div.className = 'dropdown-item';
                div.textContent = `${item.airportId} - ${item.airportName}`;
                div.addEventListener('click', () => {
                    document.querySelector('.departure .airport-code').textContent = item.airportId;
                    document.querySelector('.departure .airport-name').textContent = item.airportName;
                    resultsContainer.innerHTML = '';
                });
                resultsContainer.appendChild(div);
            });
        })
        .catch(console.error);
});


document.querySelector('#arrival-search').addEventListener('input', function () {
    const keyword = this.value.trim();
    if (keyword.length === 0) return;

	fetch(`${window.contextPath}/api/airport?keyword=${encodeURIComponent(keyword)}`)
	    .then(response => response.json())
        .then(data => {
            const resultsContainer = document.getElementById('arrival-results');
            resultsContainer.innerHTML = '';
            data.forEach(item => {
                const div = document.createElement('div');
                div.className = 'dropdown-item';
                div.textContent = `${item.airportId} - ${item.airportName}`;
                div.addEventListener('click', () => {
                    document.querySelector('.arrival .airport-code').textContent = item.airportId;
                    document.querySelector('.arrival .airport-name').textContent = item.airportName;
                    resultsContainer.innerHTML = '';
                });
                resultsContainer.appendChild(div);
            });
        })
        .catch(console.error);

}); 


document.addEventListener('DOMContentLoaded', function() {
    // 탭 기능 구현 (예약 조회, 체크인 탭 이동에 필요)
    var tabBtns = document.querySelectorAll('.booking-tab-btn');
    var bookingContents = document.querySelectorAll('.booking-content');

    tabBtns.forEach(function(btn) {
        btn.addEventListener('click', function() {
            var targetTab = btn.getAttribute('data-tab');

            // 모든 탭 버튼에서 active 클래스 제거
            tabBtns.forEach(function(b) { b.classList.remove('active'); });
            // 클릭된 탭 버튼에 active 클래스 추가
            btn.classList.add('active');

            // 모든 콘텐츠에서 active 클래스 제거
            bookingContents.forEach(function(content) { content.classList.remove('active'); });
            // 해당 콘텐츠에 active 클래스 추가
            var targetElement = document.getElementById(targetTab);
            if (targetElement) {
                targetElement.classList.add('active');
            }
        });
    });

    // '예약 조회' 탭의 비회원 조회 폼 처리
    var lookupForm = document.querySelector('#checkin .checkin-form');
    if (lookupForm) {
        lookupForm.addEventListener('submit', function(event) {
            event.preventDefault(); 

            var agreeCheckbox = lookupForm.querySelector('input[name="agreeInfo"]');
            if (agreeCheckbox && !agreeCheckbox.checked) {
                alert('[필수] 항목에 동의해주셔야 조회가 가능합니다.');
                return; 
            }
            
            var errorBox = document.querySelector('#bookingErrorBox');
            var errorMessageElement = document.querySelector('#bookingErrorMessage');
            if (errorBox) errorBox.classList.add('hidden');

            var formData = new FormData(this);

            fetch('lookup', {
                method: 'POST',
                body: new URLSearchParams(formData)
            })
            .then(function(response) {
                if (!response.ok) {
                    throw new Error('서버 응답에 문제가 발생했습니다.');
                }
                return response.json();
            })
            .then(function(data) {
                if (data.success) {
                    var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2)) || "";
                    window.location.href = contextPath + '/' + data.redirectUrl;
                } else {
                    if (errorBox && errorMessageElement) {
                        errorMessageElement.textContent = data.error || '알 수 없는 오류가 발생했습니다.';
                        errorBox.classList.remove('hidden');
                    }
                }
            })
            .catch(function(error) {
                console.error('조회 중 오류 발생:', error);
                if (errorBox && errorMessageElement) {
                    errorMessageElement.textContent = '조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
                    errorBox.classList.remove('hidden');
                }
            });
        });
    }

    // '체크인' 탭의 비회원 체크인 폼 처리
    var checkinForm = document.querySelector('#checkinLookupForm');
    if (checkinForm) {
        checkinForm.addEventListener('submit', function(event) {
            event.preventDefault(); 

            var agreeCheckbox = checkinForm.querySelector('input[name="agreeInfo"]');
            if (agreeCheckbox && !agreeCheckbox.checked) {
                alert('[필수] 항목에 동의해주셔야 조회가 가능합니다.');
                return; 
            }
            
            var errorBox = document.querySelector('#checkinErrorBox');
            var errorMessageElement = document.querySelector('#checkinErrorMessage');
            if (errorBox) errorBox.classList.add('hidden');

            var formData = new FormData(this);
            formData.append('ajax', 'true');

            fetch('checkinDetail.do', {
                method: 'POST',
                body: new URLSearchParams(formData)
            })
            .then(function(response) {
                if (!response.ok) {
                    throw new Error('서버 응답에 문제가 발생했습니다.');
                }
                return response.json();
            })
            .then(function(data) {
                if (data.success) {
                    var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2)) || "";
                    window.location.href = contextPath + '/' + data.redirectUrl;
                } else {
                    if (errorBox && errorMessageElement) {
                        errorMessageElement.textContent = data.error || '알 수 없는 오류가 발생했습니다.';
                        errorBox.classList.remove('hidden');
                    }
                }
            })
            .catch(function(error) {
                console.error('체크인 조회 중 오류 발생:', error);
                if (errorBox && errorMessageElement) {
                    errorMessageElement.textContent = '체크인 조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
                    errorBox.classList.remove('hidden');
                }
            });
        });
    }

    // 뒤로 가기 등으로 페이지 로드 시 폼 초기화 (예약 조회 및 체크인 관련)
    window.addEventListener('pageshow', function(event) {
        if (event.persisted) {
            if (lookupForm) lookupForm.reset();
            if (checkinForm) checkinForm.reset();
            var bookingErrorBox = document.querySelector('#bookingErrorBox');
            var checkinErrorBox = document.querySelector('#checkinErrorBox');
            if (bookingErrorBox) bookingErrorBox.classList.add('hidden');
            if (checkinErrorBox) checkinErrorBox.classList.add('hidden');
        }
    });

    // '체크인' 탭의 출발일 선택 옵션 생성
    function populateCheckinDateSelector() {
        var dateSelect = document.getElementById('checkinDepartureDate');
        if (!dateSelect) return;

        var today = new Date();
        dateSelect.innerHTML = '';
        for (var i = -1; i <= 2; i++) {
            var targetDate = new Date();
            targetDate.setDate(today.getDate() + i);
            var option = document.createElement('option');
            var year = targetDate.getFullYear();
            var month = String(targetDate.getMonth() + 1).padStart(2, '0');
            var day = String(targetDate.getDate()).padStart(2, '0');
            
            option.value = year + '-' + month + '-' + day;
            option.textContent = year + '년 ' + month + '월 ' + day + '일';

            if (i === 0) {
                option.selected = true;
            }
            dateSelect.appendChild(option);
        }
    }
    populateCheckinDateSelector();
});