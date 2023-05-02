<template>
    <div>
        <h1>{{ recom_card.name }}</h1>
        <img :src="recom_card.img_path" :alt="recom_card.name">
        <ul>
            <li v-for="(benefit, idx) in recom_card.benefit" :key="idx">
                {{ benefit }}
            </li>
        </ul>

        <hr>

        <div v-for="(content, idx) in recom_card.contents" :key="idx">
            <div>
                <img :src="content.img_path" :alt="content.img_path">
                <h3>
                    {{ content.name }}
                </h3>
                <p>
                    {{ content.content }}
                </p>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            recom_card: {
                id: 1,
                img_path: 'card_img_path',
                name: 'I&U 카드',
                company: '우리카드',
                card_path: 'card_path',
                benefit: [
                    '광고모델이 아이유',
                    '아이유 테마 카드 디자인',
                    '할인 좀 해준다는데 기억은 안남'
                ],
                contents: [
                    {
                        id: 1,
                        img_path: 'content_img_path1',
                        title: 'title1',
                        content: 'content1'
                    },
                    {
                        id: 2,
                        img_path: 'content_img_path2',
                        title: 'title2',
                        content: 'content2'
                    }
                ],
            }
        }
    }
}
</script>

<style></style>