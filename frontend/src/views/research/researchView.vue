<template>
  <div>
      <h1>나의 최근 소비 패턴</h1>
    <div>
      {{ research_data }}
    </div>
  </div>

  <div>
    <h2>소비 맞춤 카드 추천</h2>
    <div v-for="recom_card in recom_cards" :key="recom_card.id">
      <img src="" alt=recom_card.path >
      <div>{{ recom_card.name }} </div>
      <div>{{ recom_card.company }} </div>
    </div>
    
  </div>
</template>

<script>


export default {
  data(){
    return {
      research_data: [
        {
          classification: '여행',
          percent: 56
        },
        {
          classification: '외식',
          percent: 30
        },
        {
          classification: '쇼핑',
          percent: 15
        },
      ],
      recom_cards: [
        {
          id: '1',
          img_path: 'path',
          name: '삼성카드 어쩌고 저쩌고 카드',
          company: '삼성카드'
        },
        {
          id: '2',
          img_path: 'path',
          name: '우리카드 그시기 므시기 카드',
          company: '우리카드'
        },
        {
          id: '3',
          img_path: 'path',
          name: '국민카드 가나다라마바사아자차카드',
          company: '국민카드'
        },
      ]
    }
  }

}
</script>

<style>


</style>