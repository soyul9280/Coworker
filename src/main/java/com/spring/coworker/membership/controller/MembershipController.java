package com.spring.coworker.membership.controller;

import com.spring.coworker.global.response.PageResponse;
import com.spring.coworker.membership.dto.request.MemberShipUpdateRequest;
import com.spring.coworker.membership.dto.request.MembershipCreateRequest;
import com.spring.coworker.membership.dto.request.MembershipSearchRequest;
import com.spring.coworker.membership.dto.response.MemberShipDto;
import com.spring.coworker.membership.service.MembershipService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {
  private final MembershipService membershipService;

  @PostMapping("")
  public ResponseEntity<MemberShipDto> createMembership(
      @Valid @RequestBody MembershipCreateRequest membershipCreateRequest) {
    MemberShipDto result = membershipService.create(membershipCreateRequest);
    return ResponseEntity.ok(result);
  }

  @PatchMapping("/{membershipId}")
  public ResponseEntity<MemberShipDto> updateMembership(
      @PathVariable UUID membershipId, @Valid @RequestBody MemberShipUpdateRequest memberShipUpdateRequest) {
    MemberShipDto result = membershipService.update(membershipId, memberShipUpdateRequest);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/{membershipId}")
  public ResponseEntity<Void> deleteMembership(
      @PathVariable UUID membershipId) {
    membershipService.delete(membershipId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<PageResponse> searchMemberships(
      @ModelAttribute @Valid MembershipSearchRequest membershipSearchRequest
  ){
    PageResponse result = membershipService.searchMemberships(membershipSearchRequest);
    return ResponseEntity.ok(result);
  }

}
