package kr.ac.korea.budding.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.ac.korea.budding.dto.CertificationChallengeResponseDto;
import kr.ac.korea.budding.dto.ChallengeRequestDto;
import kr.ac.korea.budding.dto.ChallengeResponseDto;
import kr.ac.korea.budding.dto.ParticipantResponseDto;
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
        return challengeService.createChallenge(challengeRequestDto, userId);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "내가 진행 중인 챌린지들 확인하기")
    public List<ChallengeResponseDto> getMyChallenges(
            @PathVariable Long userId,
            @RequestParam ParticipationStatus status
    ) {

        return challengeService.getMyChallenges(userId, status);
    }

    @PostMapping(
            value = "/{challengeId}/certifications",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "인증 사진 업로드")
    public CertificationChallengeResponseDto certificationChallenge(
            @RequestParam Long userId,
            @PathVariable Long challengeId,
            @RequestPart("image") MultipartFile image,
            @RequestParam(value = "memo",  required = false) String memo
    ) {
        return challengeService.certificationChallenge(userId, challengeId, image, memo);
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
            value = "{challengeId}/participants/list"
    )
    @Operation(summary = "챌린지 참가자 리스트(id, nickname)")
    public List<ParticipantResponseDto> getChallengeParticipants(
            @PathVariable Long challengeId
    ) {
        return challengeService.getChallengeParticipants(challengeId);
    }
}
