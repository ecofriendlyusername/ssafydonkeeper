<template>
  <div class="DivH">
    <h1>
      <button>저번달</button>
      {{  month + '월' }}
      <button>다음달</button>
    </h1>
    
    <hr>
    <table>
      <tr>
        <td>분류</td>
        <td>일자</td>
        <td>금액</td>
        <td>장소</td>
      </tr>
      <!-- <div>분류</div> | <div>일자</div> | <div>금액</div> | <div>장소</div> -->
      <tr v-for="(dumy, idx) in dumies" :key="idx">
        <!-- {{ dumy.classification }} | {{ dumy.day }} | {{ dumy.amount }} | {{ dumy.detail }} -->
        <td>{{ dumy.classification }}</td>
        <td>{{ dumy.day }}</td>
        <td>{{ dumy.amount }}</td>
        <td>{{ dumy.detail }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {

  data() {
    return {
      month: '4',
      dumies: [
        {
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
  }

}
</script>

<style>
table {
  width: 100%
}

td {
  border: solid black 1px;

  ;
}

.DivH{
  min-height: 530px;
}
</style>

