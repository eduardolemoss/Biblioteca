document.addEventListener("DOMContentLoaded",  () => {
    
    document.getElementById("confirmar").addEventListener ("click", function (){

        event.preventDefault();

        const pessoa = {
            nome: document.getElementById('nome').value,
            email: document.getElementById('email').value,
            telefone: document.getElementById('telefone').value,
             
       };
       console.log("Dados da Pessoa a serem enviados:", pessoa)
       const notyf = new Notyf({
        duration: 3000, // Duração do alerta
        position: { x: 'center', y: 'bottom' }, // Posição da notificação
        dismissible: true // Tornar a notificação fechável
    });

       fetch('http://localhost:8080/Pessoa',{
           method: 'POST',
           headers: {
               'Content-Type' : 'application/json'
           },
           body: JSON.stringify(pessoa)
       })
       .then(response => {
           console.log("Status da resposta:", response.status)
           if(!response.ok){
               throw new Error('Erro ao inserir Cliente');
           }
           return response.json();
       })
          .then(data => {
           
            notyf.success('Cliente inserido com sucesso!');
            console.log(data)

            document.getElementById('nome').value = '';
            document.getElementById('email').value = '';
            document.getElementById('telefone').value = '';
        })
       })
       .catch(error => {
           console.error('Erro:', error);
           alert('Ocorreu um erro ao inserir.');
       });
     
    })




    