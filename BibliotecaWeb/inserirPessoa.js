
   function confirmar() {
    const pessoa = {
         nome: document.getElementById('nome').value,
         email: document.getElementById('email').value,
         telefone: document.getElementById('telefone').value,
          
    };
    console.log("Dados do livro a serem enviados:", pessoa)

    fetch('http://localhost:8080/Livro',{
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
        alert('Cliente inserido')
        console.log(data);
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao inserir.');
    });
    
}
    