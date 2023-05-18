-- challenge 더미데이터


-- 진행중
INSERT INTO challenge (CHALLENGE_ID, NAME, CONTENT, START_DATE, END_DATE, IN_PROGRESS, IS_FINISHED)
VALUES
  (10, '택시 안타기', '절약하기 위해 택시를 이용하지 않는 챌린지입니다.', '2023-05-16', '2023-05-30', true, false),
  (11, '가까운 거리는 걸어다니기', ' 가까운 거리 이동 시 택시나 대중교통을 이용하지 않고 걸어다니는 챌린지입니다.', '2023-05-14', '2023-05-28', true, false),
  (12, '자전거 출퇴근 챌린지', '2주 동안 출퇴근 시 자전거를 이용하여 교통비를 절약하는 챌린지입니다.', '2023-05-12', '2023-05-26', true, false),
  (13, '카풀 공유 이용하기', '4주 동안 외출 시 카풀 서비스를 이용하여 교통비를 절약하는 챌린지입니다.', '2023-05-16', '2023-06-13', true, false),
  (14, '대중교통 전용 챌린지', '2주 동안 대중교통만을 이용하여 차량 사용을 최소화하는 챌린지입니다.', '2023-05-12', '2023-05-26', true, false);

-- 시작전
INSERT INTO challenge (CHALLENGE_ID, NAME, CONTENT, START_DATE, END_DATE, IN_PROGRESS, IS_FINISHED)
VALUES
  (15, '평일 점심 도시락 챌린지', '평일 동안 외식 대신 집에서 도시락을 준비하여 점심을 챙기는 챌린지입니다.', '2023-05-20', '2023-06-03', false, false),
  (16, '홈트레이닝 한달 챌린지', '한달 동안 헬스장에 가지 않고 집에서 홈트레이닝을 실시하는 챌린지입니다.', '2023-05-20', '2023-06-20', false, false),
  (17, '가계부 관리 한달 챌린지', '매일 지출 내역을 정리하고 가계부를 관리하여 소비에 대한 인식을 높이는 챌린지입니다.', '2023-05-25', '2023-06-25', false, false),
  (18, '공유 경제 활용 2주 챌린지', '2주 동안 공유 경제 플랫폼을 활용하여 필요한 물건이나 서비스를 저렴하게 이용하는 챌린지입니다.', '2023-05-25', '2023-06-08', false, false),
  (19, '식비 절약 2주 챌린지', '2주 동안 식료품 가격을 비교하고 효율적인 쇼핑을 통해 식비를 절약하는 챌린지입니다.', '2023-05-25', '2023-06-08', false, false);

-- 끝남
INSERT INTO challenge (CHALLENGE_ID, NAME, CONTENT, START_DATE, END_DATE, IN_PROGRESS, IS_FINISHED)
VALUES
  (20, '일일 에너지 소비 절약 챌린지', '매일 에너지 소비를 줄이기 위해 전등을 꺼두거나 전자기기를 절전 모드로 사용하는 챌린지입니다.', '2023-05-10', '2023-05-17', false, true),
  (21, '간식 구매 자제 챌린지', '2주 동안 불필요한 간식 구매를 자제하고 집에서 간식을 준비하여 지출을 절약하는 챌린지입니다.', '2023-05-02', '2023-05-16', false, true),
  (22, '가정에서 청소하기 챌린지', '2주 동안 가정에서 청소를 스스로 처리하고 전문 청소 서비스를 이용하지 않는 챌린지입니다.', '2023-05-1', '2023-05-15', false, true),
  (23, '매일 커피 외부 구매 자제하기', '매일 외부에서 커피를 구매하지 않고 집에서 커피를 내려 마시며 비용을 절약하는 챌린지입니다.', '2023-05-01', '2023-05-08', false, true),
  (24, '쇼핑몰 브랜드 세일 기간 활용 챌린지', '2주 동안 쇼핑몰 브랜드 세일 기간에 필요한 제품을 구매하여 할인 혜택을 받고 지출을 절약하는 챌린지입니다.', '2023-05-01', '2023-05-15', false, true);

-- challenge_member 더미데이터

-- 시작 전
INSERT INTO challenge_member (CHALLENGE_MEMBER_ID, IS_SUCCESS, LOGS, CHALLENGE_ID, MEMBER_ID)
VALUES
  (35, false, '', 15, 1),
  (36, false, '', 16, 1),
  (37, false, '', 17, 1),
  (38, false, '', 18, 1),
  (39, false, '', 19, 1);

-- 진행중
INSERT INTO challenge_member (CHALLENGE_MEMBER_ID, IS_SUCCESS, LOGS, CHALLENGE_ID, MEMBER_ID)
VALUES
  (40, false, '111', 10, 1),
  (41, false, '1110', 11, 1),
  (42, false, '001110', 12, 1),
  (43, false, '111', 13, 1),
  (44, false, '0101001', 14, 1);

-- 끝
INSERT INTO challenge_member (CHALLENGE_MEMBER_ID, IS_SUCCESS, LOGS, CHALLENGE_ID, MEMBER_ID)
VALUES
(45, false, '00000000', 20, 1),
(46, true, '111110111101111', 21, 1),
(47, false, '010001000010000', 22, 1),
(48, true, '11101111', 23, 1),
(49, true, '111101111011111', 24, 1);
