document.addEventListener('DOMContentLoaded', () => {
    carregarPessoa();
    carregarLivro();

    function carregarPessoa() {
        fetch('http://localhost:8080/Pessoa')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na Rede');
            }
            return response.json();
        })
            .then(data => {
            const selectpessoa = document.getElementById('pessoa');
            if(selectpessoa){
                data.forEach(carregarPessoa => {
                    const option = document.createElement('option');
                    option.value = carregarPessoa.id;
                    option.textContent = carregarPessoa.nome;
                    selectpessoa.appendChild(option);
                })
            } else {
                console.error("Elemento 'pessoa' nao encontrado")
                    }
        })
        
        .catch(error => {
            console.error('Erro ao carregar pessoa:');
        });
     
    }
    function carregarLivro() {
        fetch('http://localhost:8080/Livro')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na Rede');
            }
            return response.json();
        })
            .then(data => {
            const selectlivro = document.getElementById('livro');
            if(selectlivro){
                data.forEach(carregarLivro => {
                    const option = document.createElement('option');
                    option.value = carregarLivro.id;
                    option.textContent = carregarLivro.nome;
                    selectlivro.appendChild(option);
                })
            }
            else{
                console.error("erro ao carregar livro")
            }
           
        })
    
        .catch(error => {
            console.error('Erro ao carregar livro:', error);
        });
    }
    
    });


function confirmar() {
    const emprestimo = {
         id_pessoa: document.getElementById('pessoa-id').value,
         id_livro: document.getElementById('livro-id').value,
        
    };
    console.log("Dados do livro a serem enviados:", emprestimo)

    fetch('http://192.168.1.7:8080/Emprestimo',{
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(emprestimo)
    })
    .then(response => {
        console.log("Status da resposta:", response.status)
        if(!response.ok){
            throw new Error('Erro ao inserir emprestimo');
        }
        return response.json();
    })
    .then(data => {
        alert('Emprestimo inserido')
        console.log(data);
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao inserir.');
    });
    
}
    