document.addEventListener('DOMContentLoaded', () => {
    carregarAutores();
    carregarGenero();

    function carregarAutores() {
        fetch('http://192.168.1.7:8080/Autor')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na Rede');
            }
            return response.json();
        })
            .then(data => {
            const selectAutor = document.getElementById('autor');
            data.forEach(autor => {
                const option = document.createElement('option');
                option.value = autor.id;
                option.textContent = autor.nome;
                selectAutor.appendChild(option);
            })
        })
    
        .catch(error => {
            console.error('Erro ao carregar autores:', error);
        });
     
    }
    function carregarGenero() {
        fetch('http://192.168.1.7:8080/Genero')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na Rede');
            }
            return response.json();
        })
            .then(data => {
            const selectgenero = document.getElementById('genero');
            data.forEach(genero => {
                const option = document.createElement('option');
                option.value = genero.id;
                option.textContent = genero.nome;
                selectgenero.appendChild(option);
            })
        })
    
        .catch(error => {
            console.error('Erro ao carregar autores:', error);
        });
    }
    
    });

    const confirmarButton = document.getElementById('confirmar');
    if (confirmarButton) {
        confirmarButton.addEventListener('click', function confirmar(event) {
            event.preventDefault();
    
    const livro = {
         titulo: document.getElementById('titulo').value,
         ISBN: document.getElementById('ISBN').value,
         autor: document.getElementById('autor').value,
         genero: document.getElementById('genero').value,
         anoPublicacao: document.getElementById('ano-publicacao').value 
    };

    fetch('http://192.168.1.7:8080/Livro',{
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(livro)
    })
    .then(response => {
        if(!response.ok){
            throw new Error('Erro ao inserir livro');
        }
        return response.json();
    })
    .then(data => {
        alert('Livro inserido')
        console.log(data);
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Ocorreu um erro ao inserir.');
    });
});
    }