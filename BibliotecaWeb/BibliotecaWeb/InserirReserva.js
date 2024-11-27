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
                selectlivro.innerHTML = '';
                data.filter(livro => livro.status === 'DISPONIVEL');
                data.forEach(Livro => {
                    const option = document.createElement('option');
                    option.value = Livro.id;
                    option.textContent = Livro.Titulo;
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
    document.getElementById("confirmar").addEventListener('click',function 
        (){
           event.preventDefault();
           
   
   
       const reserva = {
            data_reserva: document.getElementById('data_reserva').value,
            data_validade: document.getElementById('data_validade').value,
            id_pessoa: document.getElementById('pessoa').value,
            id_livro: document.getElementById('livro').value,
            
            
           
       };
       console.log("Dados do reserva a serem enviados:", reserva)
       
       const notyf = new Notyf({
           duration: 3000, // Duração do alerta
           position: { x: 'center', y: 'bottom' }, // Posição da notificação
           dismissible: true // Tornar a notificação fechável
       });
   
       fetch('http://localhost:8080/Reserva',{
           method: 'POST',
           headers: {
               'Content-Type' : 'application/json'
           },
           body: JSON.stringify(reserva)
       })
       .then(response => {
           console.log("Status da resposta:", response.status)
           if(!response.ok){
               throw new Error('Erro ao inserir reserva');
           }
           return response.json();
       })
       .then(data => {
           
           notyf.success('reserva inserido com sucesso!');
           console.log(data)
           document.getElementById('data_reserva').value = '';
           document.getElementById('pessoa').value = '';
           document.getElementById('livro').value = '';
           document.getElementById('data_validade').value = '';
           console.log(data);
   
       })
       .catch(error => {
           console.error('Erro:', error);
         
           notyf.error('Ocorreu um erro ao inserir o reserva.');
       });
       
   }
    )
})