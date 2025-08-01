package org.doit.reservation.controller;

import javax.servlet.http.HttpSession;

import org.doit.reservation.domain.CheckinVO;
import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.domain.UserVO;
import org.doit.reservation.service.CheckinService;
import org.doit.reservation.service.ReservationService;
import org.doit.reservation.util.SessionUtils;
import org.doit.reservation.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import lombok.extern.log4j.Log4j;

/**
 * 체크인 컨트롤러 (서비스 계층 사용)
 * - 체크인 조회
 * - 체크인 완료 처리
 * - 좌석 선택 및 변경
 */
@Controller
@RequestMapping("/checkin/*")
@Log4j
public class CheckinController {

    @Autowired
    private CheckinService checkinService;
    
    @Autowired
    private ReservationService reservationService;
    
    /**
     * 체크인 조회 폼 페이지
     * GET /checkin/lookup.htm
     */
    @GetMapping("/lookup.htm")
    public String checkinLookupForm(Model model) {
                    log.info("체크인 조회 폼 페이지 요청");
            return "checkin/lookup";
    }
    
    /**
     * 체크인 조회 처리
     * POST /checkin/lookup.htm
     */
    @PostMapping("/lookup.htm")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkinLookup(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName,
            @RequestParam("departureDate") String departureDate,
            @RequestParam(value = "ajax", required = false) String ajax,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        log.info("=== 체크인 조회 요청 시작 ===");
        log.info("체크인 조회 요청 - bookingId: " + bookingId + ", lastName: " + lastName + ", firstName: " + firstName + ", departureDate: " + departureDate);
        log.info("AJAX 요청 여부: " + ajax);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 예약 정보 조회 (체크인 가능 여부 확인) - 4개 값 모두 확인
            ReservationVO reservation = reservationService.findReservationByIdAndDateAndFullName(bookingId, departureDate, lastName, firstName);
            
            if (reservation == null) {
                log.warn("체크인 조회 실패 - 예약 정보 없음: bookingId: " + bookingId + ", lastName: " + lastName + ", firstName: " + firstName + ", departureDate: " + departureDate);
                return ResponseUtils.createErrorResponse("예약 정보를 찾을 수 없습니다. 예약번호, 출발일, 성, 이름을 다시 확인해주세요.");
            }
            
            // 체크인 가능 시간 검증
            boolean canCheckin = checkinService.isCheckinAvailable(reservation);
            if (!canCheckin) {
                log.warn("체크인 불가능 시간 - bookingId: " + bookingId);
                return ResponseUtils.createErrorResponse("체크인은 출발 24시간 전부터 1시간 전까지 가능합니다.");
            }
            
            // 세션에 예약 정보 저장
            SessionUtils.setReservationToSession(session, reservation, "checkinReservation");
            
            log.info("체크인 조회 성공 - bookingId: " + bookingId);
            return ResponseUtils.createSuccessResponse("checkin/detail.htm?bookingId=" + bookingId);
            
        } catch (Exception e) {
            log.error("체크인 조회 중 오류 발생", e);
            return ResponseUtils.createSystemErrorResponse("시스템 오류가 발생했습니다.");
        }
    }
    
    /**
     * 체크인 신청 페이지
     * GET /checkin/apply.htm
     */
    @GetMapping("/apply.htm")
    public String checkinApplyForm(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("체크인 신청 페이지 요청 - bookingId: " + bookingId);
        
        ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
        if (reservation == null) {
            log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
            redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
            return "redirect:/checkin/lookup.htm";
        }
        
        // 좌석 맵 조회 제거하고 바로 페이지 로드
        model.addAttribute("reservation", reservation);
        model.addAttribute("seatMap", new java.util.ArrayList<>());
        
        log.info("체크인 신청 페이지 로드 성공 - bookingId: " + bookingId);
        return "checkin/apply";
    }
    
    /**
     * 체크인 신청 폼 제출 처리
     * POST /checkin/apply.htm
     */
    @PostMapping("/apply.htm")
    public String processCheckinApply(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("flightId") String flightId,
            @RequestParam(value = "agreement1", required = false) String agreement1,
            @RequestParam(value = "agreement2", required = false) String agreement2,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        log.info("체크인 신청 폼 제출 - bookingId: " + bookingId);
        
        // 필수 동의 항목 확인
        if (agreement1 == null || agreement2 == null) {
            log.warn("필수 동의 항목이 체크되지 않음 - bookingId: " + bookingId);
            redirectAttributes.addFlashAttribute("error", "필수 동의 항목을 모두 체크해주세요.");
            return "redirect:/checkin/apply.htm?bookingId=" + bookingId;
        }
        
        // 세션에서 예약 정보 가져오기
        ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
        if (reservation == null) {
            log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
            redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
            return "redirect:/checkin/lookup.htm";
        }
        
        // 좌석 선택 여부에 따른 분기
        if (reservation.getAssignedSeat() != null && !reservation.getAssignedSeat().isEmpty()) {
            // 좌석이 이미 선택되어 있으면 예약 상세 페이지로
            log.info("좌석이 이미 선택됨 - bookingId: " + bookingId + ", seat: " + reservation.getAssignedSeat());
            return "redirect:/reservation/detail.htm?bookingId=" + bookingId;
        } else {
            // 좌석이 선택되어 있지 않으면 체크인 신청 페이지로
            log.info("좌석이 선택되지 않음 - bookingId: " + bookingId);
            return "redirect:/checkin/apply.htm?bookingId=" + bookingId;
        }
    }
    
    /**
     * 체크인 완료 페이지
     * GET /checkin/complete.htm
     */
    @GetMapping("/complete.htm")
    public String checkinComplete(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("체크인 완료 페이지 요청 - bookingId: " + bookingId);
        
        try {
            // 세션에서 예약 정보 가져오기
            ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
            if (reservation == null) {
                // 세션에 없으면 DB에서 조회
                log.info("세션에 예약 정보 없음 - DB에서 조회: " + bookingId);
                reservation = reservationService.findReservationById(bookingId);
                if (reservation != null) {
                    SessionUtils.setReservationToSession(session, reservation, "checkinReservation");
                    log.info("예약 정보를 세션에 저장: " + bookingId);
                } else {
                    log.warn("예약 정보를 찾을 수 없음 - bookingId: " + bookingId);
                    redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
                    return "redirect:/checkin/lookup.htm";
                }
            }
            
            // DB에서 최신 예약 정보 조회 (좌석 정보 포함)
            ReservationVO updatedReservation = reservationService.findReservationByIdAndName(
                bookingId, 
                reservation.getLastName(),
                null
            );
            
            if (updatedReservation != null) {
                model.addAttribute("reservation", updatedReservation);
                // 세션 업데이트
                session.setAttribute("checkinReservation", updatedReservation);
            } else {
                model.addAttribute("reservation", reservation);
            }
            
            log.info("체크인 완료 페이지 로드 성공 - bookingId: " + bookingId);
            return "checkin/complete";
            
        } catch (Exception e) {
            log.error("체크인 완료 페이지 로드 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "체크인 완료 정보를 불러오는 중 오류가 발생했습니다.");
            return "redirect:/checkin/lookup.htm";
        }
    }
    
    /**
     * 체크인 완료 처리
     * POST /checkin/complete.htm
     */
    @PostMapping("/complete.htm")
    public String completeCheckin(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("selectedSeat") String selectedSeat,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        log.info("체크인 완료 요청 - bookingId: " + bookingId + ", selectedSeat: " + selectedSeat);
        
        ReservationVO reservation = (ReservationVO) session.getAttribute("checkinReservation");
        if (reservation == null || !bookingId.equals(reservation.getBookingId())) {
            log.warn("체크인 완료 처리 - 세션 정보 없음: " + bookingId);
            redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
            return "redirect:/checkin/lookup.htm";
        }
        
        try {
            // 좌석 중복 확인
            boolean isSeatAvailable = checkinService.isSeatAvailable(reservation.getFlightId(), selectedSeat);
            if (!isSeatAvailable) {
                log.warn("선택한 좌석이 이미 선택됨 - bookingId: " + bookingId + ", seat: " + selectedSeat);
                redirectAttributes.addFlashAttribute("error", "선택한 좌석은 이미 선택되었습니다. 다른 좌석을 선택해주세요.");
                return "redirect:/checkin/apply.htm?bookingId=" + bookingId;
            }
            
            // 체크인 완료 처리
            CheckinVO checkinVO = new CheckinVO();
            checkinVO.setBookingId(bookingId);
            checkinVO.setSeatNumber(selectedSeat);
            checkinVO.setFlightId(reservation.getFlightId());
            
            // 체크인 완료 처리 (간단한 로직으로 대체)
            checkinVO.setCheckinTime(new java.sql.Timestamp(System.currentTimeMillis()));
            checkinVO.setStatus("COMPLETED");
            checkinVO.setBoardingGroup(determineBoardingGroup(selectedSeat));
            
            boolean success = checkinService.completeCheckin(checkinVO);
            
            if (success) {
                log.info("체크인 완료 성공 - bookingId: " + bookingId + ", seat: " + selectedSeat);
                session.removeAttribute("checkinReservation"); // 세션 정리
                redirectAttributes.addFlashAttribute("success", "체크인이 완료되었습니다!");
                return "redirect:/checkin/detail.htm?bookingId=" + bookingId;
            } else {
                log.error("체크인 완료 실패 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "체크인 처리 중 오류가 발생했습니다.");
                return "redirect:/checkin/complete.htm?bookingId=" + bookingId;
            }
            
        } catch (Exception e) {
            log.error("체크인 완료 처리 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "시스템 오류가 발생했습니다.");
            return "redirect:/checkin/complete.htm?bookingId=" + bookingId;
        }
    }
    
    /**
     * 체크인 상세 정보 페이지
     * GET /checkin/detail.htm
     */
    @GetMapping("/detail.htm")
    public String checkinDetail(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("체크인 상세 정보 요청 - bookingId: " + bookingId);
        
        try {
            // 세션에서 예약 정보 가져오기
            ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
            if (reservation == null) {
                log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
                return "redirect:/checkin/lookup.htm";
            }
            
            // DB에서 최신 예약 정보 조회 (좌석 정보 포함)
            ReservationVO updatedReservation = reservationService.findReservationByIdAndName(
                bookingId, 
                reservation.getLastName(),
                null
            );
            
            if (updatedReservation != null) {
                model.addAttribute("reservation", updatedReservation);
                // 세션 업데이트
                session.setAttribute("checkinReservation", updatedReservation);
            } else {
                model.addAttribute("reservation", reservation);
            }
            
            log.info("체크인 상세 정보 조회 성공 - bookingId: " + bookingId);
            return "checkin/detail";
            
        } catch (Exception e) {
            log.error("체크인 상세 정보 조회 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "체크인 정보를 불러오는 중 오류가 발생했습니다.");
            return "redirect:/checkin/lookup.htm";
        }
    }
    
    /**
     * 체크인 상세 페이지에서 좌석 확인 후 분기 처리
     * POST /checkin/processDetail.htm
     */
    @PostMapping("/processDetail.htm")
    public String processCheckinDetail(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        log.info("체크인 상세 페이지 처리 - bookingId: " + bookingId);
        
        // 세션에서 예약 정보 가져오기
        ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
        if (reservation == null) {
            log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
            redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
            return "redirect:/checkin/lookup.htm";
        }
        
        // 좌석 선택 여부에 따른 분기
        if (reservation.getAssignedSeat() != null && !reservation.getAssignedSeat().isEmpty()) {
            // 좌석이 이미 선택되어 있으면 예약 상세 페이지로
            log.info("좌석이 이미 선택됨 - bookingId: " + bookingId + ", seat: " + reservation.getAssignedSeat());
            return "redirect:/reservation/detail.htm?bookingId=" + bookingId;
        } else {
            // 좌석이 선택되어 있지 않으면 체크인 신청 페이지로
            log.info("좌석이 선택되지 않음 - bookingId: " + bookingId);
            return "redirect:/checkin/apply.htm?bookingId=" + bookingId;
        }
    }
    
    /**
     * 좌석 선택 페이지
     * GET /checkin/seat.htm
     */
    @GetMapping("/seat.htm")
    public String seatSelection(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("좌석 선택 페이지 요청 - bookingId: " + bookingId);
        
        try {
            // 로그인한 사용자 확인
            Object user = session.getAttribute("user");
            log.info("사용자 로그인 상태: " + (user != null ? "로그인됨" : "비로그인"));
            
            if (user == null) {
                // 비로그인 상태: 예약 정보를 세션에 저장하고 로그인 페이지로
                ReservationVO reservation = reservationService.findReservationById(bookingId);
                if (reservation != null) {
                    session.setAttribute("pendingSeatBookingId", bookingId);
                    session.setAttribute("pendingSeatReservation", reservation);
                    redirectAttributes.addFlashAttribute("message", "좌석 선택을 위해 로그인이 필요합니다.");
                    return "redirect:/login";
                } else {
                    log.warn("예약 정보 조회 실패 - bookingId: " + bookingId);
                    redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
                    return "redirect:/checkin/lookup.htm";
                }
            } else {
                // 로그인 상태: 기존 로직 수행
                // 세션에서 예약 정보 가져오기
                ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
                
                // 세션에 예약 정보가 없으면 DB에서 조회하여 세션에 저장
                if (reservation == null) {
                    log.info("세션에 예약 정보 없음 - DB에서 조회: " + bookingId);
                    // 예약상세페이지에서 넘어온 경우를 위해 더 정확한 조회
                    reservation = reservationService.findReservationById(bookingId);
                    if (reservation != null) {
                        SessionUtils.setReservationToSession(session, reservation, "checkinReservation");
                        log.info("예약 정보를 세션에 저장: " + bookingId);
                    } else {
                        log.warn("예약 정보를 찾을 수 없음 - bookingId: " + bookingId);
                        redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
                        return "redirect:/checkin/lookup.htm";
                    }
                }
                
                // 로그인한 사용자와 예약 정보 일치 확인 (로그인된 경우에만)
                if (user instanceof org.doit.member.model.User) {
                    org.doit.member.model.User loginUser = (org.doit.member.model.User) user;
                    
                    // 비회원 조회 후 좌석 선택인 경우 DB에서 비회원 조회값과 로그인 사용자값 비교
                    ReservationVO lookupResult = (ReservationVO) session.getAttribute("lookupResult");
                    log.info("=== 비회원 조회 세션 확인 ===");
                    log.info("lookupResult 세션 존재: " + (lookupResult != null));
                    if (lookupResult != null) {
                        log.info("lookupResult bookingId: " + lookupResult.getBookingId());
                        log.info("현재 요청 bookingId: " + bookingId);
                        log.info("bookingId 일치: " + bookingId.equals(lookupResult.getBookingId()));
                    }
                    if (lookupResult != null && bookingId.equals(lookupResult.getBookingId())) {
                        log.info("비회원 조회 후 좌석 선택 - DB 검증 시작");
                        
                        // DB에서 예약 정보 조회하여 비교
                        ReservationVO dbReservation = reservationService.findReservationByIdAndDateAndFullName(
                            lookupResult.getBookingId(),
                            lookupResult.getDepartureDate(),
                            lookupResult.getLastName(),
                            lookupResult.getFirstName()
                        );
                        
                        if (dbReservation != null) {
                            // 로그인한 사용자와 DB의 예약 정보 비교
                            String loginUserName = loginUser.getKoreanName();
                            String dbPassengerName = dbReservation.getFullPassengerName();
                            
                            log.info("DB 검증 - 로그인 사용자: " + loginUserName + ", DB 예약 승객명: " + dbPassengerName);
                            log.info("DB 검증 - firstName: " + dbReservation.getFirstName() + ", lastName: " + dbReservation.getLastName());
                            log.info("DB 검증 - 로그인 사용자 영어명: " + loginUser.getEnglishName());
                            
                            boolean nameMatches = isUserNameMatch(loginUser, dbReservation);
                            
                            if (!nameMatches) {
                                log.warn("DB 검증 실패 - 비회원 조회값과 로그인 사용자 불일치");
                                redirectAttributes.addFlashAttribute("error", "해당 예약의 사용자만 좌석을 선택할 수 있습니다.");
                                return "redirect:/dashboard";
                            } else {
                                log.info("DB 검증 성공 - 비회원 조회값과 로그인 사용자 일치");
                            }
                        } else {
                            log.warn("DB에서 예약 정보를 찾을 수 없음");
                            redirectAttributes.addFlashAttribute("error", "예약 정보를 찾을 수 없습니다.");
                            return "redirect:/dashboard";
                        }
                    } else {
                        // 일반적인 경우 이름 검증 수행
                        String loginUserName = loginUser.getKoreanName(); // 로그인한 사용자의 한국어 이름
                        String reservationPassengerName = reservation.getFullPassengerName(); // 예약의 승객명 (firstName + lastName)
                        
                        log.info("사용자 일치 확인 - 로그인 사용자 이름: " + loginUserName + ", 예약 승객명: " + reservationPassengerName);
                        
                        // 이름 비교 (한국어/영어 이름 매칭)
                        boolean nameMatches = isUserNameMatch(loginUser, reservation);
                        
                        // 예약의 사용자 이름과 로그인한 사용자 이름이 일치하지 않으면 로그인 페이지로 리다이렉트
                        if (!nameMatches) {
                            log.warn("사용자 불일치 - 로그인 사용자 이름: " + loginUserName + ", 예약 승객명: " + reservationPassengerName);
                            redirectAttributes.addFlashAttribute("error", "해당 예약의 사용자만 좌석을 선택할 수 있습니다.");
                            return "redirect:/login";
                        }
                    }
                }
                
                // 선택된 좌석 목록 조회 추가
                List<String> occupiedSeats = checkinService.getOccupiedSeats(reservation.getFlightId());
                
                model.addAttribute("reservation", reservation);
                model.addAttribute("occupiedSeats", occupiedSeats);
                
                log.info("좌석 선택 페이지 로드 성공 - bookingId: " + bookingId);
                return "checkin/seat";
            }
            
        } catch (Exception e) {
            log.error("좌석 선택 페이지 로드 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "좌석 정보를 불러오는 중 오류가 발생했습니다.");
            return "redirect:/checkin/lookup.htm";
        }
    }
    

    
    /**
     * 좌석 선택 저장 처리
     * POST /checkin/seat.htm
     */
    @PostMapping("/seat.htm")
    public String saveSeatSelection(
            @RequestParam("bookingId") String bookingId,
            @RequestParam("seatId") String seatId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        log.info("좌석 선택 저장 요청 - bookingId: " + bookingId + ", seatId: " + seatId);
        
        try {
            // 로그인한 사용자 확인 (로그인 없이도 접근 허용)
            Object user = session.getAttribute("user");
            log.info("좌석 저장 - 사용자 로그인 상태: " + (user != null ? "로그인됨" : "비로그인"));
            
            // 세션에서 예약 정보 가져오기
            ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
            if (reservation == null) {
                log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
                return "redirect:/checkin/lookup.htm";
            }
            
            // 로그인한 사용자와 예약 정보 일치 확인 (로그인된 경우에만)
            if (user != null && user instanceof org.doit.member.model.User) {
                org.doit.member.model.User loginUser = (org.doit.member.model.User) user;
                
                log.info("좌석 저장 - 사용자 일치 확인 시작");
                
                // 이름 비교 (한국어/영어 이름 매칭)
                boolean nameMatches = isUserNameMatch(loginUser, reservation);
                
                // 예약의 사용자 이름과 로그인한 사용자 이름이 일치하지 않으면 로그인 페이지로 리다이렉트
                if (!nameMatches) {
                    log.warn("좌석 저장 - 사용자 불일치 - 로그인 사용자와 예약 승객 정보 불일치");
                    redirectAttributes.addFlashAttribute("error", "해당 예약의 사용자만 좌석을 선택할 수 있습니다.");
                    return "redirect:/login";
                }
            } else {
                log.info("비로그인 사용자 - 좌석 저장 시 사용자 검증 생략");
            }
            
            // 좌석 선택 여부 확인
            if (seatId == null || seatId.trim().isEmpty()) {
                log.warn("좌석이 선택되지 않음 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "좌석을 선택해주세요.");
                return "redirect:/checkin/seat.htm?bookingId=" + bookingId;
            }
            
            // 좌석 저장 처리
            boolean success = checkinService.saveSeatSelection(bookingId, seatId);
            
            if (success) {
                log.info("좌석 선택 저장 성공 - bookingId: " + bookingId + ", seatId: " + seatId);
                redirectAttributes.addFlashAttribute("success", "좌석이 성공적으로 선택되었습니다!");
                return "redirect:/checkin/complete.htm?bookingId=" + bookingId;
            } else {
                log.error("좌석 선택 저장 실패 - bookingId: " + bookingId + ", seatId: " + seatId);
                redirectAttributes.addFlashAttribute("error", "이미 선택된 좌석이거나 좌석 선택 중 오류가 발생했습니다.");
                return "redirect:/checkin/seat.htm?bookingId=" + bookingId;
            }
            
        } catch (Exception e) {
            log.error("좌석 선택 저장 처리 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "시스템 오류가 발생했습니다.");
            return "redirect:/checkin/seat.htm?bookingId=" + bookingId;
        }
    }
    
    /**
     * 좌석번호에 따른 탑승 그룹 결정 (Service Layer로 이동 예정)
     */
    private String determineBoardingGroup(String seatNumber) {
        if (seatNumber == null || seatNumber.trim().isEmpty()) {
            return "3"; // 기본 그룹
        }
        
        // 좌석번호에서 행 번호 추출
        String rowStr = seatNumber.replaceAll("[A-Z]", "");
        int row = Integer.parseInt(rowStr);
        
        // 프레스티지석 (28-43행)
        if (row >= 28 && row <= 43) {
            return "1";
        }
        // 비즈니스석 (7-10행)
        else if (row >= 7 && row <= 10) {
            return "2";
        }
        // 이코노미석 (기타)
        else {
            return "3";
        }
    }
    
    /**
     * 좌석 중복 검증 (AJAX)
     * POST /checkin/validateSeat.htm
     */
    @PostMapping("/validateSeat.htm")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validateSeat(
            @RequestParam("seatId") String seatId,
            @RequestParam("bookingId") String bookingId,
            HttpSession session) {
        
        log.info("좌석 중복 검증 요청 - bookingId: " + bookingId + ", seatId: " + seatId);
        
        try {
            // 세션에서 예약 정보 가져오기
            ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
            if (reservation == null) {
                return ResponseUtils.createErrorResponse("예약 정보를 찾을 수 없습니다.");
            }
            
            // 좌석 중복 검증
            boolean isAvailable = checkinService.isSeatAvailableForSelection(reservation.getFlightId(), seatId);
            
            if (isAvailable) {
                return ResponseUtils.createSuccessResponse("좌석 선택 가능");
            } else {
                return ResponseUtils.createErrorResponse("이미 선택된 좌석입니다.");
            }
            
        } catch (Exception e) {
            log.error("좌석 중복 검증 중 오류 발생", e);
            return ResponseUtils.createSystemErrorResponse("좌석 검증 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 좌석 가격 조회 (AJAX)
     * POST /checkin/getSeatPrice.htm
     */
    @PostMapping("/getSeatPrice.htm")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSeatPrice(
            @RequestParam("seatId") String seatId,
            @RequestParam("bookingId") String bookingId,
            HttpSession session) {
        
        log.info("좌석 가격 조회 요청 - bookingId: " + bookingId + ", seatId: " + seatId);
        
        try {
            // 세션에서 예약 정보 가져오기
            ReservationVO reservation = SessionUtils.getReservationFromSession(session, bookingId);
            if (reservation == null) {
                return ResponseUtils.createErrorResponse("예약 정보를 찾을 수 없습니다.");
            }
            
            // 좌석 가격 조회
            int seatPrice = checkinService.getSeatPrice(reservation.getFlightId(), seatId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("price", seatPrice);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(response);
            
        } catch (Exception e) {
            log.error("좌석 가격 조회 중 오류 발생", e);
            return ResponseUtils.createSystemErrorResponse("좌석 가격 조회 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 로그인 사용자와 예약 정보의 이름 매칭을 검증하는 유틸리티 메서드
     * 한국어/영어 이름의 다양한 조합을 포괄적으로 비교
     * 
     * @param loginUser 로그인한 사용자 정보
     * @param reservation 예약 정보
     * @return 이름이 일치하면 true, 그렇지 않으면 false
     */
    private boolean isUserNameMatch(org.doit.member.model.User loginUser, ReservationVO reservation) {
        if (loginUser == null || reservation == null) {
            return false;
        }
        
        String loginKoreanName = loginUser.getKoreanName();
        String loginEnglishName = loginUser.getEnglishName();
        String reservationFirstName = reservation.getFirstName();
        String reservationLastName = reservation.getLastName();
        String reservationPassengerName = reservation.getFullPassengerName();
        
        log.debug("이름 매칭 검증 시작 - 로그인 한국어명: " + loginKoreanName + 
                ", 로그인 영어명: " + loginEnglishName + 
                ", 예약 firstName: " + reservationFirstName + 
                ", 예약 lastName: " + reservationLastName + 
                ", 예약 전체명: " + reservationPassengerName);
        
        // 1. 한국어 이름과 전체 승객명 비교
        if (loginKoreanName != null && reservationPassengerName != null && 
            loginKoreanName.equals(reservationPassengerName)) {
            log.debug("매칭 성공: 한국어 이름 = 전체 승객명");
            return true;
        }
        
        // 2. 영어 이름과 전체 승객명 비교
        if (loginEnglishName != null && reservationPassengerName != null && 
            loginEnglishName.equals(reservationPassengerName)) {
            log.debug("매칭 성공: 영어 이름 = 전체 승객명");
            return true;
        }
        
        // 3. 한국어 이름과 lastName 비교
        if (loginKoreanName != null && reservationLastName != null && 
            loginKoreanName.equals(reservationLastName)) {
            log.debug("매칭 성공: 한국어 이름 = lastName");
            return true;
        }
        
        // 4. 영어 이름과 firstName 비교
        if (loginEnglishName != null && reservationFirstName != null && 
            loginEnglishName.equals(reservationFirstName)) {
            log.debug("매칭 성공: 영어 이름 = firstName");
            return true;
        }
        
        // 5. firstName + lastName 조합과 비교
        if (reservationFirstName != null && reservationLastName != null) {
            String fullName = reservationFirstName + " " + reservationLastName;
            
            // 한국어 이름과 firstName + lastName 조합 비교
            if (loginKoreanName != null && loginKoreanName.equals(fullName)) {
                log.debug("매칭 성공: 한국어 이름 = firstName + lastName");
                return true;
            }
            
            // 영어 이름과 firstName + lastName 조합 비교
            if (loginEnglishName != null && loginEnglishName.equals(fullName)) {
                log.debug("매칭 성공: 영어 이름 = firstName + lastName");
                return true;
            }
        }
        
        // 6. 공백 제거 후 비교 (공백으로 인한 매칭 실패 방지)
        if (loginKoreanName != null && reservationPassengerName != null) {
            String trimmedKorean = loginKoreanName.trim();
            String trimmedPassenger = reservationPassengerName.trim();
            if (trimmedKorean.equals(trimmedPassenger)) {
                log.debug("매칭 성공: 공백 제거 후 한국어 이름 = 전체 승객명");
                return true;
            }
        }
        
        if (loginEnglishName != null && reservationPassengerName != null) {
            String trimmedEnglish = loginEnglishName.trim();
            String trimmedPassenger = reservationPassengerName.trim();
            if (trimmedEnglish.equals(trimmedPassenger)) {
                log.debug("매칭 성공: 공백 제거 후 영어 이름 = 전체 승객명");
                return true;
            }
        }
        
        log.debug("이름 매칭 실패 - 모든 조합이 일치하지 않음");
        return false;
    }
} 