package kr.ac.korea.budding.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.ac.korea.budding.dto.*;
import kr.ac.korea.budding.enums.ParticipationStatus;
import kr.ac.korea.budding.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/{userId}")
    @Operation(summary = "챌린지 생성하기")
    public ChallengeResponseDto createChallenge(
            @PathVariable Long userId,
            @RequestBody ChallengeRequestDto challengeRequestDto
    ) {
        return challengeService.createChallenge(userId, challengeRequestDto);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "내가 진행 중인 챌린지들 확인하기")
    public List<ChallengeResponseDto> getMyChallenges(
            @PathVariable Long userId,
            @RequestParam ParticipationStatus status
    ) {

        return challengeService.getMyChallenges(userId, status);
    }

    @PostMapping(
            value = "/{challengeId}/users/{userId}/checkIn",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "인증 사진 업로드")
    public CheckInChallengeResponseDto CheckInChallenge(
            @PathVariable Long challengeId,
            @PathVariable Long userId,
            @RequestPart("image") MultipartFile image,
            @RequestParam(value = "memo",  required = false) String memo
    ) {
        return challengeService.checkInChallenge(challengeId, userId, image, memo);
    }

    @GetMapping(
            value = "/{challengeId}/participants/count"
    )
    @Operation(summary = "챌린지 참가자 수")
    public Long countChallengeParticipants(
            @PathVariable Long challengeId
    ) {
        return challengeService.countChallengeParticipants(challengeId);
    }

    @GetMapping(
            value = "/{challengeId}/participants"
    )
    @Operation(summary = "챌린지 참가자 리스트(id, nickname)")
    public List<ParticipantResponseDto> getChallengeParticipants(
            @PathVariable Long challengeId
    ) {
        return challengeService.getChallengeParticipants(challengeId);
    }

    @GetMapping(
            value = "/{challengeId}/users/{userId}"
    )
    @Operation(summary = "유저의 챌린지 1개 상세보기")
    public ChallengeDetailResponseDto getChallengeDetailByUser(
            @PathVariable Long challengeId,
            @PathVariable Long userId
    ) {
        return challengeService.getChallengeDetailByUser(challengeId, userId);
    }

    @PostMapping(
            value = "/{challengeId}/users/{userId}"
    )
    @Operation(summary = "유저가 챌린지에 참여")
    public ChallengeResponseDto participateChallenge(
            @PathVariable Long challengeId,
            @PathVariable Long userId
    ) {
        return challengeService.participateChallenge(challengeId, userId);
    }
}
