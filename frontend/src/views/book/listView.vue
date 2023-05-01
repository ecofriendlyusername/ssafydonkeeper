<template>
  <div class="DivH">

        <h1>
          <div style="display:flex; justify-content:center; align-items: center; font-size: 90%;">
            <div id="beforeBtn">◀</div>
            <div>{{  month + '월' }}</div>
            <div id="afterBtn">▶</div>
          </div>
        </h1>

      <!-- <span id="beforeBtn">◀</span>
        {{  month + '월' }}
      <span id="afterBtn">▶</span> -->

    <div style="display:flex; justify-content: center; font-weight: bold; margin-top: -10px; font-size: 110%;">
      <div id="listTitle">
        지출
      </div>
      <div style="margin-left:40px;">
        {{total_spend}}
      </div>
    </div>

    <div style="display:flex; justify-content: center; font-weight: bold; font-size: 110%;">
      <div id="listTitle">
        수입
      </div>
      <div style="margin-left:40px; color: #58BB84;">
        {{total_incom}}
      </div>
    </div>
    
    <div style="height:10px; width:100%; background-color:#F0F2F5; margin-top:15px; margin-bottom:10px;"></div>

    <div style="display:flex; justify-content:space-between; font-weight: bold; margin-bottom: 20px; padding: 0px 10px;">
      <div>전체내역</div>
      <div>+ 추가</div>
    </div>
    <div style="height:2px; width:100%; background-color:#F0F2F5; margin-top:-10px; margin-bottom: 8px;"></div>


    <div v-for="(dumy, idx) in dumies" :key="idx" v-on:click="this.$router.push('/book/'+dumy.memberId)"
    style="display:flex; justify-content:space-between; align-items: center; border-radius: 8px; background-color: #F0F2F5; margin: 10px 5px; padding: 10px 15px;">
        <div>
          <div style="font-weight:bold">
            {{ dumy.classification }}
          </div>
          <div style="font-size:75%; color: gray; margin-top: 2px;">
            {{month}}.{{ dumy.day }}
          </div>
        </div>
        <div>
          <div style="font-weight:bold">₩ {{ dumy.amount }}</div>
          <div style="font-size:75%; color: gray; margin-top: 2px;">{{ dumy.detail }}</div>
        </div>
      </div>








    <!-- <table>
      <tr>
        <td>분류</td>
        <td>장소</td>
        <td>금액</td>
      </tr>
  
      <tr v-for="(dumy, idx) in dumies" :key="idx" v-on:click="this.$router.push('/book/'+dumy.memberId)">
     
        <td>
          <div style="font-weight:bold">
            {{ dumy.classification }}
          </div>
          <div style="font-size:75%; color: gray;">
            {{month}}.{{ dumy.day }}
          </div>
        </td>
        <td>{{ dumy.detail }}</td>
        <td>{{ dumy.amount }}</td>
      </tr>
    </table> -->
    
  </div>
</template>

<script>
export default {

  data() {
    return {
      month: 4,
      total_incom: 200000,
      total_spend: 100000,
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

/* td {
  border: solid black 1px;

  ;
} */

.DivH{
  min-height: 530px;
}

#listTitle {
  color:#808080;
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

