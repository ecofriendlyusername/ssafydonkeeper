<template>
  <div>
    <h1>{{ data?.year }}년 {{ data?.month }}월 나의 예산</h1>

    <button v-on:click="this.$router.push('/budget/set')">예산 설정하기</button> <br> <br>

    <h3 style="display:flex; margin-top:-10px; margin-left: 20px;">총 예산: {{ priceToString(data?.total_amount) }} ₩</h3>

    <div class="baseBar">
      <div class="gaugeBar"></div>
      <div class="dot_line"></div>
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
              <div style="font-weight: bold; margin-left: 10px; font-size: 16px;">{{ item?.name }}</div>
            </div>
            <div>
              {{ priceToString(spend_data.filter(el => item?.classificationId == el.categoryId)[0]?.amount) }}원 사용
            </div>
            <div style="color:#808080;">{{ priceToString((item?.amount - spend_data.filter(el => item?.classificationId ==
              el.categoryId)[0]?.amount) / item.amount * 100) }}%</div>
            <div style="font-weight: bold;">{{ priceToString(item?.amount - spend_data.filter(el => item?.classificationId
              == el.categoryId)[0]?.amount) }}원 남음 </div>
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

      this.axios.get(process.env.VUE_APP_API_URL + `/statistics/monthlyspendingbycat/${this.year}/${this.month}`)
        .then(res => {
          // console.log(`/statistics/monthlyspendingbycat/${this.year}/${this.month}`);
          console.log(res.data);
          this.spend_data = res.data
          this.total_spend = res.data.reduce((o, t) => o + t.amount, 0)
        })
        .then(() => {
          if (this.data.total_amount == 0) {
            alert("예산 정보가 없습니다.\n예산 설정 페이지로 이동합니다.")
            this.$router.push('/budget/set')
          }
        })
        .then(()=>{
          this.axios.get(process.env.VUE_APP_API_URL + `/account-book/budget/all/${this.year}/${this.month}`)
            .then(res => {
              this.data = res.data;
              console.log(res.data);
            }).then(() => {
              const gauge = document.querySelector(".gaugeBar");
              let tmp = 100 - (this.data.total_amount - this.total_spend) / this.data.total_amount * 100
              console.log(tmp);
              let limit = tmp < 100 ? tmp : 100;
              if (limit == 100) {
                gauge.style.backgroundColor = "red";
              }

              for (let i = 0; i < limit; i++) {
                setTimeout(() => { gauge.style.width = `${i}%`; }, i * 10)
              }
              
              const dot_line = document.querySelector(".dot_line");
              let tmp2 = new Date().getDate() / new Date(this.year, this.month, 0).getDate() * 100
              console.log(tmp2);
              for (let i = 0; i < tmp2; i++) {
                setTimeout(() => { dot_line.style.width = `${i}%`; }, i * 15)
              }
            })
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
  display: flex;
  position: relative;
}

.gaugeBar {
  width: 0px;
  height: 28px;
  background-color: #4D82E6;
  border-radius: 25px;
  position: absolute;
}

.dot_line {
  height: 28px;
  border-right: 4px dotted black;
  z-index: 99;
  width: 0px;
  position: absolute;
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