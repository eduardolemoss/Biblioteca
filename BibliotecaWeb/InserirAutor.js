function confirmar() {
    
   
    
    const autor = {
         autor: document.getElementById('nome').value
         
    };
    console.log("Dados do livro a serem enviados:", autor)

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
            throw new Error('Erro ao inserir livro');
        }
        return response.json();
    })
    .then(data => {
        alert('Autor inserido')
        console.log(data);
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao inserir.');
    });
    
}
    