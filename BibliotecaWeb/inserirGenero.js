function confirmar() {
    
    const genero = {
         nome: document.getElementById('nome').value
         
    };
    console.log("Dados do livro a serem enviados:", genero)

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
        alert('Genero inserido')
        console.log(data);
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao inserir.');
    });
    
}
    