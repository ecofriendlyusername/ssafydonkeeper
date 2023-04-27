<template>
  <div class="DivH">
    <div>
      날짜: {{ dumy.year }} / {{dumy.month }} / {{dumy.day }}
    </div>
    <div>
      자산 분류: {{ dumy.classification }}
    </div>
    <div>
      금액: {{ dumy.amount }}
    </div>
    <div>
      내용: {{ dumy.detail }}
    </div>
    <div>
      메모: {{ dumy.memo }}
    </div>

    <button>수정하기</button>
    

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

</style>