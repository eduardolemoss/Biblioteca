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
                data.forEach(pessoa => {
                    const option = document.createElement('option');
                    option.value = pessoa.Id;
                    option.textContent = pessoa.nome;
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
                    option.textContent = carregarLivro.Titulo;
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
    
   
 document.getElementById("confirmar").addEventListener('click',function confirmar ()
    {

    const emprestimo = {
         data_emprestimo: document.getElementById('data_emprestimo').value,
         data_devolucao: document.getElementById('data_devolucao').value,
         id_pessoa: document.getElementById('pessoa').value,
         id_livro: document.getElementById('livro').value,
        
    };
    console.log("Dados do emprestimo a serem enviados:", emprestimo)
    console.log("Dados do livro a serem enviados:", livro);
    const notyf = new Notyf({
        duration: 3000, // Duração do alerta
        position: { x: 'center', y: 'bottom' }, // Posição da notificação
        dismissible: true // Tornar a notificação fechável
    });

    fetch('http://localhost:8080/emprestimos',{
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
        
        notyf.success('Emprestimo inserido com sucesso!');
        console.log(data)
        document.getElementById('data_emprestimo').value = '';
        document.getElementById('data_devolucao').value = '';
        document.getElementById('pessoa').value = '';
        document.getElementById('livro').value = '';
        console.log(data);

    })
    .catch(error => {
        console.error('Erro:', error);
      
        notyf.error('Ocorreu um erro ao inserir o emprestimo.');
    });
    
}
 )
});
