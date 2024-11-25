 document.addEventListener('DOMContentLoaded', function(){

    document.querySelector("#confirmar").addEventListener('click',function confirmar ()
    {
        
       
        
        const autor = {
             nome: document.getElementById('nome').value
             
        };
        console.log("Dados do livro a serem enviados:", autor)
        console.log("Dados do livro a serem enviados:", livro);
        const notyf = new Notyf({
            duration: 3000, // Duração do alerta
            position: { x: 'center', y: 'bottom' }, // Posição da notificação
            dismissible: true // Tornar a notificação fechável
        });
    
        fetch('http://localhost:8080/Autor',{
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(autor)
        })
        .then(response => {
            console.log("Status da resposta:", response.status)
            if(!response.ok){
                throw new Error('Erro ao inserir autor');
            }
            return response.json();
        })
        .then(data => {
           
            notyf.success('Autor inserido com sucesso!');
            console.log(data)

            document.getElementById('nome').value = '';
        })
        .catch(error => {
            console.error('Erro:', error);
          
            notyf.error('Ocorreu um erro ao inserir o autor.');
        });
        
    });
        
 })
 
 