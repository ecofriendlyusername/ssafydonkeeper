<template>
  <div class="DivH">
    <h1>
      <div style="display:flex; justify-content:center; align-items: center; font-size: 90%;">
        <div id="beforeBtn" v-on:click="before()">◀</div>
        <div>{{ year }}.{{ month }}.</div>
        <div id="afterBtn" v-on:click="afte()">▶</div>
      </div>
    </h1>

    <div v-on:click="IS = true"
      style="display:flex; justify-content: center; font-weight: bold; margin-top: -10px; font-size: 110%;">
      <div id="listTitle">
        지출
      </div>
      <div style="margin-left:40px;">
        {{ total_spend }}
      </div>
    </div>

    <div v-on:click="IS = false" style="display:flex; justify-content: center; font-weight: bold; font-size: 110%;">
      <div id="listTitle">
        수입
      </div>
      <div style="margin-left:40px; color: #58BB84;">
        {{ total_incom }}
      </div>
    </div>

    <div style="height:10px; width:100%; background-color:#F0F2F5; margin-top:15px; margin-bottom:10px;"></div>

    <div v-if="IS">
      <div
        style="display:flex; justify-content:space-between; font-weight: bold; margin-bottom: 20px; padding: 0px 10px;">
        <div>소비내역</div>
        <div>+ 추가</div>
      </div>
      <div style="height:2px; width:100%; background-color:#F0F2F5; margin-top:-10px; margin-bottom: 8px;"></div>
      <div v-for="(dumy, idx) in spend_dumies" :key="idx"
        v-on:click="this.$router.push({ name: 'bookDetail', query: { id: dumy.id, classification:'spending' } })"
        style="display:flex; justify-content:space-between; align-items: center; border-radius: 8px; background-color: #F0F2F5; margin: 10px 5px; padding: 10px 15px;">
        <div>
          <div style="font-weight:bold">
            {{ dumy.classification }}
          </div>
          <div style="font-size:75%; color: gray; margin-top: 2px;">
            {{ dumy.month }}.{{ dumy.day }}
          </div>
        </div>
        <div>
          <div style="font-weight:bold">₩ {{ dumy.amount }}</div>
          <div style="font-size:75%; color: gray; margin-top: 2px;">{{ dumy.detail }}</div>
        </div>
      </div>
    </div>
    <div v-else>
      <div
        style="display:flex; justify-content:space-between; font-weight: bold; margin-bottom: 20px; padding: 0px 10px;">
        <div>수입내역</div>
        <div>+ 추가</div>
      </div>
      <div style="height:2px; width:100%; background-color:#F0F2F5; margin-top:-10px; margin-bottom: 8px;"></div>
      <div v-for="(dumy, idx) in incom_dumies" :key="idx"
        v-on:click="this.$router.push({ name: 'bookDetail', query: { id: dumy.id, classification:'income' } })"
        style="display:flex; justify-content:space-between; align-items: center; border-radius: 8px; background-color: #F0F2F5; margin: 10px 5px; padding: 10px 15px;">
        <div>
          <div style="font-weight:bold">
            {{ dumy.classification }}
          </div>
          <div style="font-size:75%; color: gray; margin-top: 2px;">
            {{ dumy.month }}.{{ dumy.day }}
          </div>
        </div>
        <div>
          <div style="font-weight:bold">₩ {{ dumy.amount }}</div>
          <div style="font-size:75%; color: gray; margin-top: 2px;">{{ dumy.detail }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from 'axios'
export default {

  data() {
    return {
      year: new Date().getFullYear(),
      month: new Date().getMonth(),
      page_number: 1,
      page_size: 10,

      IS: true,

      total_incom: 200000,
      total_spend: 100000,
      incom_dumies: [
        {
          'id': 1,
          "classification": "월급",
          "year": 2023,
          "month": 4,
          "day": 12,
          "amount": 164000,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        },
        {
          'id': 2,
          "classification": "돈주움",
          "year": 2023,
          "month": 4,
          "day": 19,
          "amount": 27000,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        },
        {
          'id': 3,
          "classification": "복권",
          "year": 2023,
          "month": 4,
          "day": 28,
          "amount": 29700,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        },
      ],
      spend_dumies: [
        {
          'id': 1,
          "classification": "외식",
          "year": 2023,
          "month": 4,
          "day": 12,
          "amount": 164000,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        },
        {
          'id': 2,
          "classification": "영화",
          "year": 2023,
          "month": 4,
          "day": 19,
          "amount": 27000,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        },
        {
          'id': 3,
          "classification": "배달",
          "year": 2023,
          "month": 4,
          "day": 28,
          "amount": 29700,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        },
        {
          'id': 4,
          "classification": "영화",
          "year": 2023,
          "month": 4,
          "day": 22,
          "amount": 32000,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        },
        {
          'id': 5,
          "classification": "영화",
          "year": 2023,
          "month": 4,
          "day": 27,
          "amount": 19000,
          "detail": "some detail",
          "memo": "some memo",
          "memberId": 4
        }
      ]
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      console.log(this.year, this.month, this.page_number)
      // axios.get(`'/account-book/spending/${this.year}/${this.month}?page=${this.page_number}&size=${this.page_size}'`)
      // .then(res => {
      //   console.log(res.data)
      // })
    },
    before() {
      this.month = this.month === 1 ? 12 : this.month - 1;
      this.year = this.month === 12 ? this.year - 1 : this.year;
      this.page_number = 1;
      this.getData()
    },
    afte() {
      this.month = this.month === 12 ? 1 : this.month + 1;
      this.year = this.month === 1 ? this.year + 1 : this.year;
      this.page_number = 1;
      this.getData()
    }
  }

}
</script>

<style>
table {
  width: 100%
}

/* td {
  border: solid black 1px;

  ;
} */

.DivH {
  min-height: 530px;
}

#listTitle {
  color: #808080;
}

#beforeBtn {
  font-size: 60%;
  margin: 0px 10px;
}

#afterBtn {
  font-size: 60%;
  margin: 0px 10px;
}
</style>

