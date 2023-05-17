<template>
  <div>
    <h1>{{ data?.year }}년 {{ data?.month }}월 나의 예산</h1>

    <button v-on:click="this.$router.push('/budget/set')">예산 설정하기</button> <br> <br>

    <h3 style="display:flex; margin-top:-10px; margin-left: 20px;">총 예산: {{ priceToString(data?.total_amount) }} ₩</h3>

    <div class="baseBar">
      <div class="gaugeBar"></div>
    </div>

    <div id="budgetTitle" style="font-size: 17px;">
      <div> <span style="font-weight:bold;">사용 예산:</span> {{ priceToString(total_spend) }} ₩</div>
    </div>
    <div id="budgetTitle" style="font-size: 17px;">
      <div> <span style="font-weight:bold;">남은 예산:</span> {{ priceToString(data.total_amount - total_spend) }} ₩</div>
    </div>
    <div id="budgetTitle" style="font-size: 17px;">
      <div> <span style="font-weight:bold;">오늘까지 권장 지출액:</span> {{ priceToString(data.total_amount * (new Date().getDate()
        / (new Date(year, month, 0).getDate() - new Date(year, month - 1, 0).getDay()))) }} ₩</div>{{ }}
    </div>
    <div style="margin-top:45px;">
      <h3 id="budgetTitle">카테고리별 예산</h3>
      <div v-for="(item, idx) in data.datas" :key="idx" class="cell">
        <div>
          <div id="budgetTitle" style="display:flex; align-items:center; justify-content: space-between;">
            <div style="display:flex; align-items:center;">
              <div id="budgetImg"></div>
              <div style="font-weight: bold; margin-left: 10px; font-size: 16px;">{{ item.name }}</div>
            </div>
            <div>
              {{ priceToString(spend_data.filter(el => item.classificationId == el.categoryId)[0].amount) }}원 사용
            </div>
            <div style="color:#808080;">{{ priceToString((item.amount - spend_data.filter(el => item.classificationId ==
              el.categoryId)[0].amount) / item.amount * 100) }}%</div>
            <div style="font-weight: bold;">{{ priceToString(item.amount - spend_data.filter(el => item.classificationId
              == el.categoryId)[0].amount) }}원 남음 </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      data: [],
      spend_data: [],
      total_spend: 0
    }
  },

  methods: {
    getData() {
      this.axios.get(process.env.VUE_APP_API_URL + `/account-book/budget/all/${this.year}/${this.month}`)
        .then(res => {
          this.data = res.data;
          console.log(res.data);
        }).then(() => {
          const gauge = document.querySelector(".gaugeBar");
          let tmp = 100 - (this.data?.total_budget - this.spend_data?.spend_budget) / this.data?.total_budget * 100
          gauge.style.width = `${tmp}%`;

          if (this.data.amount == 1) {
            // this.axios.get(process.env.VUE_APP_API_URL + `/`)
            this.$router.push('/budget/set')
          }
        })
      this.axios.get(process.env.VUE_APP_API_URL + `/statistics/monthlyspendingbycat/${this.year}/${this.month}`)
        .then(res => {
          // console.log(`/statistics/monthlyspendingbycat/${this.year}/${this.month}`);
          console.log(res.data);
          this.spend_data = res.data
          this.total_spend = res.data.reduce((o, t) => o + t.amount, 0)
        })

    },
    priceToString(price) {
      return Math.round(price).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
  },
  mounted() {
    this.getData()
    
  }

}
</script>

<style>
.baseBar {
  width: 300px;
  height: 28px;
  background-color: #E5E5E5;
  margin: 0 auto;
  padding: 2px;
  border-radius: 25px;
}

.gaugeBar {
  width: 200px;
  height: 28px;
  background-color: #4D82E6;
  border-radius: 25px;
}

.cell {
  display: contents;
  align-items: center;
}

#budgetTitle {
  display: flex;
  margin-left: 20px;
  margin-top: 10px;
}

#budgetImg {
  background-color: #DEF0FF;
  width: 35px;
  height: 35px;
  border-radius: 20px;
}
</style>