document.addEventListener('DOMContentLoaded', function (){

    const confirmar = document.getElementById('confirmar').addEventListener('click', function (){
        event.preventDefault();


        const genero = {
            nome: document.getElementById('nome').value
            
       };

       console.log("Dados do livro a serem enviados:", genero)
       
       const notyf = new Notyf({
           duration: 3000, // Duração do alerta
           position: { x: 'center', y: 'bottom' }, // Posição da notificação
           dismissible: true // Tornar a notificação fechável
       });
    
       fetch('http://localhost:8080/Genero',{
           method: 'POST',
           headers: {
               'Content-Type' : 'application/json'
           },
           body: JSON.stringify(genero)
       })
       .then(response => {
           console.log("Status da resposta:", response.status)
           if(!response.ok){
               throw new Error('Erro ao inserir livro');
           }
           return response.json();
       })
       .then(data => {
        notyf.success('Genero inserido com sucesso!');
        console.log(data)

        document.getElementById('nome').value = '';
        console.log(data);

       })
       .catch(error => {
        console.error('Erro:', error);
      
        notyf.error('Ocorreu um erro ao inserir o genero.');
    });
    })

   
   
})    
    

    