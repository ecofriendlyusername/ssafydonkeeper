<template>
  <div>
    <h1>이번달 나의 예산</h1>
    <p>총 예산: {{ req.total_budget }} ₩</p>

    <div class="baseBar">
      <div class="gageBar"></div>
    </div>

    <p>사용 예산: {{ req.spend_budget }} ₩</p>
    <p>남은 예산: {{ req.total_budget - req.spend_budget }} ₩</p>
    <p>오늘까지 권장 지출액: 1,200,000 ₩</p>

    <div>
      <h3>카테고리별 예산</h3>
      <div v-for="(budget_category, idx) in req.budget_categories" :key="idx">
        [{{ budget_category.category }}] 전체 예산: {{ budget_category.total_budget }} 사용 예산: {{ budget_category.spend_budget
        }}
        퍼센트{{ 100 - (budget_category.total_budget - budget_category.spend_budget) / budget_category.total_budget * 100 }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      req: {
        total_budget: 2000000,
        spend_budget: 1500000,
        budget_categories: [
          {
            category: '쇼핑',
            total_budget: 500000,
            spend_budget: 400000,
          },
          {
            category: '여행',
            total_budget: 400000,
            spend_budget: 600000,
          },
          {
            category: '식사',
            total_budget: 1100000,
            spend_budget: 800000,
          }
        ]
      }
    }
  },
  // mounted: {

  // }

}
</script>

<style>
.baseBar {
  width: 300px;
  height: 14px;
  background-color: #000;
  margin: 0 auto;
  padding: 2px;
}

.gageBar {

  width: 200px;
  height: 14px;
  background-color: #ff0;
}
</style>