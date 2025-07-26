package org.doit.reservation.controller;

import javax.servlet.http.HttpSession;

import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.domain.UserVO;
import org.doit.reservation.domain.CancelReservationVO;
import org.doit.reservation.service.ReservationService;
import org.doit.reservation.util.SessionUtils;
import org.doit.reservation.util.ResponseUtils;
import org.doit.member.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ResponseBody; // 반드시 추가
import org.springframework.http.ResponseEntity; // 반드시 추가
import java.util.HashMap; // 반드시 추가
import java.util.Map; // 반드시 추가

/**
 * 예약 조회 컨트롤러 (MyBatis 직접 사용)
 * - 비회원 예약 조회
 * - 회원 예약 조회 
 * - 예약 상세 정보 조회
 */
@Controller
@RequestMapping("/reservation/*")
@Log4j
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    /**
     * 예약 조회 폼 페이지 (비회원용)
     * GET /reservation/lookup.htm
     */
    @GetMapping("/lookup.htm")
    public String lookupForm(Model model) {
        log.info("=== GET 요청: 비회원 예약 조회 폼 페이지 요청 ===");
        log.info("View name returned: reservation/lookup");
        return "reservation/lookup";
    }
    
    /**
     * 비회원 예약 조회 처리
     * POST /reservation/lookup.htm
     */
    @PostMapping("/lookup.htm") 
    @ResponseBody // 이 메서드의 반환 값이 HTTP 응답 본문에 직접 쓰여지도록 지정
    public ResponseEntity<Map<String, Object>> lookupReservation(
            @RequestParam(value = "bookingId", required = false) String bookingId,
            @RequestParam(value = "departureDate", required = false) String departureDate,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "firstName", required = false) String firstName,
            HttpSession session) {
        
        log.info("비회원 예약 조회 요청 - bookingId: " + bookingId + ", departureDate: " + departureDate + ", lastName: " + lastName + ", firstName: " + firstName);
        
        // 파라미터 유효성 검사
        if (bookingId == null || bookingId.trim().isEmpty()) {
            log.warn("예약번호 누락");
            return ResponseUtils.createErrorResponse("예약번호를 입력해주세요.");
        }
        if (departureDate == null || departureDate.trim().isEmpty()) {
            log.warn("출발일 누락");
            return ResponseUtils.createErrorResponse("출발일을 선택해주세요.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            log.warn("성(영문) 누락");
            return ResponseUtils.createErrorResponse("성(영문)을 입력해주세요.");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            log.warn("이름(영문) 누락");
            return ResponseUtils.createErrorResponse("이름(영문)을 입력해주세요.");
        }
        
        Map<String, Object> response = new HashMap<>(); // JSON 응답을 구성할 Map
        try {
            log.info("실제 DB 조회 시작 - 4개 필드 검증");
            ReservationVO reservation = reservationService.findReservationByIdAndDateAndFullName(bookingId, departureDate, lastName, firstName);
            log.info("DB 조회 결과: " + (reservation != null ? "성공" : "실패"));
            
            if (reservation != null) {
                log.info("조회된 예약 정보 - bookingId: " + reservation.getBookingId() + ", 승객명: " + reservation.getPassengerName());
                // 조회 성공 시 세션에 저장 (이것은 /reservation/detail.htm에서 사용될 수 있습니다)
                SessionUtils.setReservationToSession(session, reservation, "lookupResult"); 
                log.info("비회원 예약 조회 성공 - bookingId: " + bookingId);
                
                return ResponseUtils.createSuccessResponse("reservation/detail.htm?bookingId=" + reservation.getBookingId());
            } else {
                // 조회 실패
                log.warn("비회원 예약 조회 실패 - 4개 필드 불일치");
                return ResponseUtils.createErrorResponse("예약 정보를 찾을 수 없습니다. 예약번호, 출발일, 승객명을 모두 정확히 입력해주세요.");
            }
            
        } catch (Exception e) {
            log.error("비회원 예약 조회 중 오류 발생", e);
            return ResponseUtils.createSystemErrorResponse("시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    }
    
    /**
     * 예약 상세 정보 페이지
     * GET /reservation/detail.htm
     */
    @GetMapping("/detail.htm")
    public String reservationDetail(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) { // 이 경우 RedirectAttributes는 사용되지 않음
        
        log.info("예약 상세 정보 요청 - bookingId: " + bookingId);
        
        try {
            ReservationVO reservation = null;
            
            // 1. 세션에서 비회원 조회 결과 확인
            // 이전에 lookupReservation에서 세션에 저장한 lookupResult를 사용
            ReservationVO lookupResult = (ReservationVO) session.getAttribute("lookupResult");
            
            // 2. 체크인에서 넘어온 경우 checkinReservation 세션 확인
            ReservationVO checkinReservation = (ReservationVO) session.getAttribute("checkinReservation");
            
            // 세션에서 예약 정보 확인
            log.debug("lookupResult: " + (lookupResult != null ? lookupResult.getBookingId() : "null"));
            log.debug("checkinReservation: " + (checkinReservation != null ? checkinReservation.getBookingId() : "null"));
            
            if (lookupResult != null && bookingId.equals(lookupResult.getBookingId())) {
                // DB에서 최신 정보 재조회
                reservation = reservationService.findReservationByIdAndName(
                    bookingId, 
                    lookupResult.getLastName(),
                    null
                );
                if (reservation != null) {
                    log.info("DB에서 비회원 예약 정보 재조회 성공");
                } else {
                    reservation = lookupResult;
                    log.info("세션에서 비회원 예약 정보 조회 성공");
                }
            } else if (checkinReservation != null && bookingId.equals(checkinReservation.getBookingId())) {
                // DB에서 최신 정보 재조회
                reservation = reservationService.findReservationByIdAndName(
                    bookingId, 
                    checkinReservation.getLastName(),
                    null
                );
                if (reservation != null) {
                    log.info("DB에서 체크인 예약 정보 재조회 성공");
                } else {
                    reservation = checkinReservation;
                    log.info("세션에서 체크인 예약 정보 조회 성공");
                }
            } else {
                // 3. 로그인된 사용자의 예약 조회 (또는 세션에 없을 경우 DB에서 재조회)
                UserVO user = (UserVO) session.getAttribute("user");
                if (user != null) {
                    reservation = reservationService.findReservationByIdAndUserId(bookingId, String.valueOf(user.getId()));
                    log.info("로그인 사용자 예약 정보 조회 - id: " + user.getId());
                } else {
                    // 임시: 로그인 검증 없이 DB에서 재조회 (비회원 체크인 지원)
                    log.info("비회원 체크인에서 넘어온 경우 - bookingId: " + bookingId);
                    // DB에서 예약 정보 재조회 (체크인 세션에서 가져온 정보로)
                    if (checkinReservation != null) {
                        reservation = reservationService.findReservationByIdAndName(
                            bookingId, 
                            checkinReservation.getLastName(),
                            null
                        );
                        log.info("DB에서 체크인 예약 정보 재조회 성공");
                    }
                }
            }
            
            if (reservation != null) {
                model.addAttribute("reservation", reservation);
                
                // 예약변경을 위해 세션에 예약 정보 저장
                SessionUtils.setReservationToSession(session, reservation, "changeReservation");
                
                // 좌석 변경을 위해 세션에 예약 정보 저장
                SessionUtils.setReservationToSession(session, reservation, "reservation");
                
                log.info("예약 상세 정보 조회 성공 - bookingId: " + bookingId);
                return "reservation/detail"; // 상세 JSP 뷰 반환
            } else {
                log.warn("예약 상세 정보 조회 실패 - bookingId: " + bookingId);
                model.addAttribute("error", "예약 정보를 찾을 수 없습니다.");
                return "reservation/lookup"; // lookup 페이지로 돌아감
            }
            
        } catch (Exception e) {
            log.error("예약 상세 정보 조회 중 오류 발생", e);
            model.addAttribute("error", "시스템 오류가 발생했습니다.");
            return "reservation/lookup"; // lookup 페이지로 돌아감
        }
    }
    
    /**
     * 회원 예약 목록 조회 (대시보드용)
     * GET /mylist.htm
     */
    @GetMapping("/mylist.htm")
    public String myReservationList(HttpSession session, Model model) {
        log.info("회원 예약 목록 조회 요청");
        
        UserVO user = (UserVO) session.getAttribute("user");
        if (user == null) {
            log.warn("비로그인 사용자의 예약 목록 접근 시도");
            return "redirect:/auth/login.htm";
        }
        
        try {
            // 사용자의 모든 예약 목록 조회 (Service 사용)
            var reservationList = reservationService.getUserReservations(user.getUserId());
            model.addAttribute("reservationList", reservationList);
            model.addAttribute("user", user);
            
            log.info("회원 예약 목록 조회 성공 - userId: " + user.getUserId() + ", 예약 수: " + reservationList.size());
            
            return "reservation/mylist"; // mylist JSP 뷰 반환
            
        } catch (Exception e) {
            log.error("회원 예약 목록 조회 중 오류 발생", e);
            model.addAttribute("error", "예약 목록을 불러오는 중 오류가 발생했습니다.");
            return "reservation/mylist"; // mylist JSP 뷰 반환
        }
    }
    
    /**
     * 예약취소 폼 페이지
     * GET /reservation/cancel.htm
     */
    @GetMapping("/cancel.htm")
    public String cancelReservationForm(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("예약취소 폼 페이지 요청 - bookingId: " + bookingId);
        
        try {
            // 로그인 상태 확인
            User user = (User) session.getAttribute("user");
            
            if (user == null) {
                // 비로그인 상태: 예약 정보를 세션에 저장하고 로그인 페이지로
                ReservationVO reservation = reservationService.findReservationById(bookingId);
                if (reservation != null) {
                    session.setAttribute("pendingCancelBookingId", bookingId);
                    session.setAttribute("pendingCancelReservation", reservation);
                    redirectAttributes.addFlashAttribute("message", "예약 취소를 위해 로그인이 필요합니다.");
                    return "redirect:/login";
                } else {
                    log.warn("예약 정보 조회 실패 - bookingId: " + bookingId);
                    redirectAttributes.addFlashAttribute("error", "취소할 예약 정보를 찾을 수 없습니다.");
                    return "redirect:/reservation/lookup.htm";
                }
            } else {
                // 로그인 상태: 기존 로직 수행
                CancelReservationVO cancelInfo = reservationService.getCancelReservationInfo(bookingId, null);
                
                if (cancelInfo == null) {
                    log.warn("예약취소 정보 조회 실패 - bookingId: " + bookingId);
                    redirectAttributes.addFlashAttribute("error", "취소할 예약 정보를 찾을 수 없습니다.");
                    return "redirect:/reservation/lookup.htm";
                }
                
                // 디버깅 로그 추가
                log.info("예약취소 정보 조회 성공 - bookingId: " + bookingId);
                log.info("cancelInfo: " + cancelInfo);
                log.info("baseFare: " + cancelInfo.getBaseFare());
                log.info("penaltyFee: " + cancelInfo.getPenaltyFee());
                log.info("totalRefundAmount: " + cancelInfo.getTotalRefundAmount());
                
                // Model에 데이터 전달
                model.addAttribute("cancelInfo", cancelInfo);
                
                log.info("예약취소 폼 페이지 로드 성공 - bookingId: " + bookingId);
                return "reservation/cancel";
            }
            
        } catch (Exception e) {
            log.error("예약취소 폼 페이지 로드 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "예약취소 정보를 불러오는 중 오류가 발생했습니다.");
            return "redirect:/reservation/mylist.htm";
        }
    }
    
    /**
     * 예약취소 처리
     * POST /reservation/cancel.htm
     */
    @PostMapping("/cancel.htm")
    public String processCancelReservation(
            @RequestParam("bookingId") String bookingId,
            @RequestParam(value = "reason", required = false) String reason,
            @RequestParam(value = "agreement1", required = false) String agreement1,
            @RequestParam(value = "agreement2", required = false) String agreement2,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        log.info("예약취소 처리 요청 - bookingId: " + bookingId);
        
        try {
            // 필수 동의 항목 확인
            if (agreement1 == null || agreement2 == null) {
                log.warn("필수 동의 항목 미체크 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "필수 동의 항목을 모두 체크해주세요.");
                return "redirect:/reservation/cancel.htm?bookingId=" + bookingId;
            }
            
            // 예약취소 처리
            boolean success = reservationService.cancelReservation(bookingId, reason != null ? reason : "사용자 요청");
            
            if (success) {
                log.info("예약취소 성공 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("success", "예약이 성공적으로 취소되었습니다.");
                return "redirect:/reservation/lookup.htm";
            } else {
                log.error("예약취소 실패 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "예약취소 처리 중 오류가 발생했습니다.");
                return "redirect:/reservation/cancel.htm?bookingId=" + bookingId;
            }
            
        } catch (Exception e) {
            log.error("예약취소 처리 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "시스템 오류가 발생했습니다.");
            return "redirect:/reservation/cancel.htm?bookingId=" + bookingId;
        }
    }
    
    /**
     * 예약변경 선택 페이지
     * GET /changeReservationSelect.htm
     */
    @GetMapping("/changeReservationSelect.htm")
    public String changeReservationSelect(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("예약변경 선택 페이지 요청 - bookingId: " + bookingId);
        
        try {
            // 로그인한 사용자 확인
            Object user = session.getAttribute("user");
            log.info("사용자 로그인 상태: " + (user != null ? "로그인됨" : "비로그인"));
            
            // 비로그인 상태인 경우
            if (user == null) {
                log.info("비로그인 상태 - 예약 정보를 세션에 저장하고 로그인 페이지로 리다이렉트");
                
                // 예약 정보 조회
                ReservationVO reservation = reservationService.findReservationById(bookingId);
                if (reservation == null) {
                    log.warn("예약 정보 조회 실패 - bookingId: " + bookingId);
                    redirectAttributes.addFlashAttribute("error", "예약 정보를 찾을 수 없습니다.");
                    return "redirect:/reservation/lookup.htm";
                }
                
                // 세션에 예약 정보 저장
                session.setAttribute("pendingChangeBookingId", bookingId);
                session.setAttribute("pendingChangeReservation", reservation);
                
                redirectAttributes.addFlashAttribute("message", "예약 변경을 위해 로그인이 필요합니다.");
                log.info("예약 정보 세션 저장 완료 - bookingId: " + bookingId);
                return "redirect:/login";
            }
            
            // 로그인 상태인 경우 - 기존 로직 수행
            ReservationVO reservation = (ReservationVO) session.getAttribute("changeReservation");
            if (reservation == null || !bookingId.equals(reservation.getBookingId())) {
                log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
                return "redirect:/reservation/lookup.htm";
            }
            
            model.addAttribute("reservation", reservation);
            log.info("예약변경 선택 페이지 로드 성공 - bookingId: " + bookingId);
            return "reservation/changeSelect";
            
        } catch (Exception e) {
            log.error("예약변경 선택 페이지 로드 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "예약변경 정보를 불러오는 중 오류가 발생했습니다.");
            return "redirect:/reservation/lookup.htm";
        }
    }
    
    /**
     * 예약변경 검색 페이지
     * POST /reservation/changeReservationSearch.htm
     */
    @PostMapping("/changeReservationSearch.htm")
    public String changeReservationSearch(
            @RequestParam("bookingId") String bookingId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("예약변경 검색 페이지 요청 - bookingId: " + bookingId);
        
        try {
            // 세션에서 예약 정보 가져오기
            ReservationVO reservation = (ReservationVO) session.getAttribute("changeReservation");
            if (reservation == null || !bookingId.equals(reservation.getBookingId())) {
                log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
                redirectAttributes.addFlashAttribute("error", "잘못된 접근입니다.");
                return "redirect:/reservation/lookup.htm";
            }
            
            model.addAttribute("reservation", reservation);
            log.info("예약변경 검색 페이지 로드 성공 - bookingId: " + bookingId);
            return "reservation/changeSearch";
            
        } catch (Exception e) {
            log.error("예약변경 검색 페이지 로드 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "예약변경 정보를 불러오는 중 오류가 발생했습니다.");
            return "redirect:/reservation/lookup.htm";
        }
    }
}