document.addEventListener('DOMContentLoaded', () => {


    

    carregarAutores();
    carregarGenero();

    
    

    function carregarAutores() {
        fetch('http://localhost:8080/Autor')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na Rede');
            }
            return response.json();
        })
            .then(data => {
            const selectAutor = document.getElementById('autor');
            if(selectAutor){
                data.forEach(autor => {
                    const option = document.createElement('option');
                    option.value = autor.id;
                    option.textContent = autor.nome;
                    selectAutor.appendChild(option);
                })
            } else {
                console.error("Elemento 'autor' nao encontrado")
                    }
        })
        
        .catch(error => {
            console.error('Erro ao carregar autores:');
        });
     
    }
    function carregarGenero() {
        fetch('http://localhost:8080/Genero')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na Rede');
            }
            return response.json();
        })
            .then(data => {
            const selectgenero = document.getElementById('genero');
            if(selectgenero){
                data.forEach(genero => {
                    const option = document.createElement('option');
                    option.value = genero.id;
                    option.textContent = genero.nome;
                    selectgenero.appendChild(option);
                })
            }
            else{
                console.error("erro ao carregar generos")
            }
           
        })
    
        .catch(error => {
            console.error('Erro ao carregar autores:', error);
        });
    }
    
  
    document.getElementById("confirmar").addEventListener('click', function () {

        event.preventDefault();


        const livro = {
            titulo: document.getElementById('titulo').value,
            ISBN: document.getElementById('ISBN').value,
            id_autor: document.getElementById('autor').value,
            id_genero: document.getElementById('genero').value,
            ano_publicacao: document.getElementById('ano-publicacao').value,
            foto : document.getElementById('foto').value
        };
    
        console.log("Dados do livro a serem enviados:", livro);
        const notyf = new Notyf({
            duration: 3000, // Duração do alerta
            position: { x: 'center', y: 'bottom' }, // Posição da notificação
            dismissible: true // Tornar a notificação fechável
        });
        

       
        fetch('http://localhost:8080/Livro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(livro)
        })
        .then(response => {
            console.log("Status da resposta:", response.status);
            if (!response.ok) {
                throw new Error('Erro ao inserir livro');
            }
            return response.json();
        })
        .then(data => {
           
            notyf.success('Livro inserido com sucesso!');
            console.log(data)


            document.getElementById('titulo').value = '';
            document.getElementById('ISBN').value = '';
            document.getElementById('autor').value = '';
            document.getElementById('genero').value = '';
            document.getElementById('ano-publicacao').value = '';
    
            console.log(data);
        })
        .catch(error => {
            console.error('Erro:', error);
          
            notyf.error('Ocorreu um erro ao inserir o livro.');
        });
    });
});
