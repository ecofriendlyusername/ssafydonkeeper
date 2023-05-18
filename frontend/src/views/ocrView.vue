<template>
  <div>
    <h1>영수증 촬영</h1>
    <video id="video" autoplay></video>
    <button @click="capture">촬영</button>
    <canvas id="canvas" style="display:none;"></canvas>
  </div>
</template>

<script>
export default {
  methods: {
    capture() {

      const video = document.getElementById('video');
      const canvas = document.getElementById('canvas');
      const context = canvas.getContext('2d');

      // 비디오의 크기에 맞게 캔버스 크기 설정
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;

      // 비디오 프레임을 캔버스에 그립니다.
      context.drawImage(video, 0, 0, video.videoWidth, video.videoHeight);

      // 캔버스를 Blob으로 변환합니다.
      canvas.toBlob((blob) => {
        if (blob) {
          // 새로운 폼 데이터를 생성합니다.
          const formData = new FormData();

          // 이미지 파일을 폼 데이터에 추가합니다.
          formData.append('image', blob, 'capture.jpg');

          // 이미지를 서버로 전송합니다.
          this.axios
            .post(process.env.VUE_APP_API_URL + `/account-book/ocr`, formData, {
              headers: {
                'Content-Type': 'multipart/form-data',
              },
            })
            .then((response) => {
              console.log(response); // 성공 시 처리
            })
            .catch((error) => {
              console.log(error); // 에러 시 처리
            });
        } else {
          console.log('No image data captured.');
        }
      }, 'image/jpeg');
    },
  },

  mounted() {
    const video = document.getElementById('video');

    if (navigator.mediaDevices.getUserMedia) {
      navigator.mediaDevices
        .getUserMedia({ video: true })
        .then((stream) => {
          video.srcObject = stream;
        })
        .catch((error) => {
          console.log('Something went wrong!', error);
        });
    }
  },
};
</script>
