<template>
  <div class="DivH">
    <div style="display: inline-block; height:5px; width:90%; background-color:black; margin-top:15px; margin-bottom:10px;"></div>
    <div style="font-size:120%; font-weight: bold;">
      {{ dumy.detail }}
      <!-- CU(메인타워점) -->
    </div>
    <div style="display:inline-block; height:2px; width:80%; background-color:#F0F2F5; margin-top:10px; margin-bottom:10px;"></div>

    <div style="width:80%; display: inline-block;">
      <div style="display:flex; justify-content:space-between;">
        <div style="color:#808080">승인일시</div>
        <div>
          {{ dumy.year }} / {{dumy.month }} / {{dumy.day }}
        </div>
      </div>

      <div style="display:flex; justify-content:space-between; margin-top:15px;">
        <div style="color:#808080">상품분류</div>
        <div>
          {{ dumy.classification }}
        </div>
      </div>

      <div style="display:flex; justify-content:space-between; margin-top:15px;">
        <div style="color:#808080">메모</div>
        <div>
          {{ dumy.memo }}
        </div>
      </div>

      <div style="display:inline-block; height:2px; width:100%; background-color:#F0F2F5; margin-top:10px; margin-bottom:10px;"></div>
      
      <div style="display:flex; justify-content:space-between; font-size: 120%;">
        <div>상품금액</div>
        <div style="color:#EA4B4E; font-weight:bold">
          {{ dumy.amount }}원
        </div>
      </div>
    </div>
    <br>

    <button id="detailBtn">수정하기</button>
    

  </div>
</template>

<script>
export default {
  data() {
    return {
      dumy: {
        "id": 19,
        "classification": "배달",
        "year": 2023,
        "month": 4,
        "day": 23,
        "amount": 24800,
        "detail": "some detail",
        "memo": "some memo",
        "memberId": 4
      },
    }
  }
}
</script>

<style>
#detailBtn {
  margin-top: 40px;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
}
</style>