document.addEventListener('DOMContentLoaded', function() {

    // 1. 관리자 페이지의 비행기 레이아웃 데이터 객체
    const aircraftData = {
        "model1": {
            name: "보잉 787-9 (기본형)",
            prestigeLayout: ['A', 'B', ' ', 'D', 'E', ' ', 'H', 'J'],
            prestigeRows: [7, 8, 9, 10],
            economyLayout: ['A', 'B', 'C', ' ', 'D', 'E', 'F', ' ', 'G', 'H', 'J'],
            economySections: [
                { startRow: 28, endRow: 43, info: "일반석", removedSeats: { 43: ['D','E','F'] } },
                { startRow: 44, endRow: 57, info: "일반석", removedSeats: { 44: ['C','G','D','E','F'], 45: ['D','E','F'], 57: ['A','J'] } }
            ],
            frontFacilitiesHTML: '<div class="facility-row"><div class="facility-group"><span class="facility-item">🍽</span><span class="facility-item">🍽</span></div></div><div class="facility-row"><div class="facility-group"><span class="facility-item exit-facility" style="margin-left: 20px;">EXIT</span></div><div class="facility-group"><span class="facility-item">🍽</span></div><div class="facility-group"><span class="facility-item">🚻♿</span><span class="facility-item exit-facility" style="margin-right: 20px;">EXIT</span></div></div>',
            prestigeEndFacilitiesHTML: '<div class="exit-row"><span class="exit">EXIT</span><span class="exit">EXIT</span></div><div class="facility-row"><div class="facility-group"><span class="facility-item">🚻♿</span></div><div class="facility-group"><span class="facility-item">🍽</span></div><div class="facility-group"><span class="facility-item">🚻</span></div></div>',
            economy1EndFacilitiesHTML: '<div class="exit-row"><span class="exit">EXIT</span><span class="exit">EXIT</span></div><div class="facility-row"><div class="facility-group"><span class="facility-item">🚻♿</span><span class="facility-item">🚻</span></div><div class="facility-group"><span class="facility-item">🍽</span></div><div class="facility-group"><span class="facility-item">🚻</span></div></div>',
            rearFacilitiesHTML: '<div class="exit-row"><span class="exit">EXIT</span><span class="exit">EXIT</span></div><div class="facility-row"><div class="facility-group"><span class="facility-item">🚻</span></div><div class="facility-group"><span class="facility-item">🍽</span><span class="facility-item">🍽</span></div><div class="facility-group"><span class="facility-item">🚻</span></div></div><div class="facility-row"><div class="facility-group"><span class="facility-item">🍽</span><span class="facility-item">🍽</span></div></div>'
        }
    };

    // 2. 관리자 페이지의 비행기 좌석 생성 함수
    function renderAircraft(modelKey) {
        const airplaneDiv = document.getElementById("airplaneContainer");
        if (!airplaneDiv) return;
        
        const model = aircraftData[modelKey];
        let htmlContent = '';

        if (!model || !model.prestigeLayout) {
            airplaneDiv.innerHTML = '<p>좌석 정보를 불러올 수 없습니다.</p>';
            return;
        }

        htmlContent += model.frontFacilitiesHTML || '';
        htmlContent += '<div class="section-divider"></div>';

        // Prestige Class
        htmlContent += '<p class="info-text">Prestige Class</p>';
        model.prestigeRows.forEach(r => {
            htmlContent += `<div class="visual-seat-row"><div class="row-number">${r}</div><div class="row-content">`;
            model.prestigeLayout.forEach(c => { 
                const seatNumber = r + c;
                const isOccupied = typeof occupiedSeats !== 'undefined' && occupiedSeats && occupiedSeats.includes(seatNumber);
                const occupiedClass = isOccupied ? ' occupied' : '';
                htmlContent += (c === ' ') ? '<div class="aisle"></div>' : `<div class="seat${occupiedClass}" data-row="${r}" data-seat="${c}"><span class="seat-letter">${c}</span></div>`; 
            });
            htmlContent += '</div></div>';
        });

        htmlContent += model.prestigeEndFacilitiesHTML || '';
        
        // Economy Sections
        model.economySections.forEach((section, index) => {
            htmlContent += '<div class="section-divider"></div><p class="info-text">' + section.info + '</p>';
            for (let r = section.startRow; r <= section.endRow; r++) {
                htmlContent += `<div class="visual-seat-row"><div class="row-number">${r}</div><div class="row-content">`;
                model.economyLayout.forEach(c => {
                    if (c === ' ') { 
                        htmlContent += '<div class="aisle"></div>'; 
                    } else {
                        let isRemoved = (section.removedSeats && section.removedSeats[r] && section.removedSeats[r].includes(c));
                        if (isRemoved) {
                            htmlContent += '<div class="seat-removed"></div>';
                        } else {
                            const seatNumber = r + c;
                            const isOccupied = typeof occupiedSeats !== 'undefined' && occupiedSeats && occupiedSeats.includes(seatNumber);
                            const occupiedClass = isOccupied ? ' occupied' : '';
                            htmlContent += `<div class="seat${occupiedClass}" data-row="${r}" data-seat="${c}"><span class="seat-letter">${c}</span></div>`;
                        }
                    }
                });
                htmlContent += '</div></div>';
            }
            if (index === 0 && model.economy1EndFacilitiesHTML) { htmlContent += model.economy1EndFacilitiesHTML; }
        });

        htmlContent += model.rearFacilitiesHTML || '';
        airplaneDiv.innerHTML = htmlContent;
        
        // 좌석 렌더링 후 이벤트 리스너 다시 바인딩
        bindSeatClickEvents();
        
        // 이미 선택된 좌석들의 클릭 이벤트 제거
        if (typeof occupiedSeats !== 'undefined' && occupiedSeats && occupiedSeats.length > 0) {
            occupiedSeats.forEach(function(seatNumber) {
                const row = seatNumber.substring(0, seatNumber.length - 1);
                const seat = seatNumber.substring(seatNumber.length - 1);
                const seatElement = document.querySelector(`[data-row="${row}"][data-seat="${seat}"]`);
                if (seatElement) {
                    seatElement.onclick = null; // 클릭 이벤트 제거
                }
            });
        }
    }

    // 3. 사용자 인터랙션 관련 로직
    const seatForm = document.getElementById('seatForm');
    if (!seatForm) return;

    const hiddenSeatIdInput = document.getElementById('seatId');
    const selectedSeatDisplay = document.getElementById('selectedSeatDisplay');

    function bindSeatClickEvents() {
        const allSeats = document.querySelectorAll('.seat');
        allSeats.forEach(seat => {
            seat.addEventListener('click', function() {
                // 중복 좌석 선택 방지 로직 추가
                if (this.classList.contains('occupied')) {
                    alert('이미 선택된 좌석입니다.');
                    return;
                }
                
                // 서버에 중복 검증 요청
                const seatId = this.dataset.row + this.dataset.seat;
                const bookingId = document.querySelector('input[name="bookingId"]').value;
                
                // AJAX로 중복 검증
                fetch('/checkin/validateSeat.htm', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'seatId=' + seatId + '&bookingId=' + bookingId
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // 중복이 아니면 좌석 선택 처리
                        selectSeat(this, seatId);
                    } else {
                        // 중복이면 알림
                        alert(data.error || '이미 선택된 좌석입니다.');
                        // 해당 좌석을 occupied로 표시
                        this.classList.add('occupied');
                        this.onclick = null;
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('좌석 검증 중 오류가 발생했습니다.');
                });
            });
        });
    }
    
    // 좌석 선택 처리 함수
    function selectSeat(seatElement, seatId) {
        // 이전에 선택된 좌석의 하이라이트 제거
        const currentSelected = document.querySelector('.seat-selected-highlight');
        if(currentSelected) {
            currentSelected.classList.remove('seat-selected-highlight');
        }

        // 새로 선택된 좌석에 하이라이트 추가
        seatElement.classList.add('seat-selected-highlight');
        
        const seatType = (parseInt(seatElement.dataset.row) >= 28 && parseInt(seatElement.dataset.row) <= 43) ? '프레스티지석' : '일반석';

        // 사이드바 정보 업데이트
        if(selectedSeatDisplay) {
            selectedSeatDisplay.innerHTML = `
                <p class="seat-number">${seatId}</p>
                <p class="seat-type">${seatType}</p>
            `;
        }

        // 숨겨진 input 값 설정
        if(hiddenSeatIdInput) {
            hiddenSeatIdInput.value = seatId;
        }
        
        // 가격 업데이트
        updateSeatPrice(seatId);
    }
    
    // 가격 업데이트 함수
    function updateSeatPrice(seatId) {
        const bookingId = document.querySelector('input[name="bookingId"]').value;
        
        fetch('/checkin/getSeatPrice.htm', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'seatId=' + seatId + '&bookingId=' + bookingId
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                const priceElement = document.querySelector('.additional-charge strong');
                if (priceElement) {
                    priceElement.textContent = '₩' + data.price.toLocaleString();
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    seatForm.addEventListener('submit', function(event) {
        if (!hiddenSeatIdInput.value) {
            event.preventDefault();
            alert('좌석을 선택해주세요.');
        }
    });

    // 4. 페이지 로드 시 비행기 그리기
    renderAircraft('model1');

}); 