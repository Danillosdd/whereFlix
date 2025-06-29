'use strict';

/**
 * navbar variables
 */

const navOpenBtn = document.querySelector("[data-menu-open-btn]");
const navCloseBtn = document.querySelector("[data-menu-close-btn]");
const navbar = document.querySelector("[data-navbar]");
const overlay = document.querySelector("[data-overlay]");

const navElemArr = [navOpenBtn, navCloseBtn, overlay];

for (let i = 0; i < navElemArr.length; i++) {

  navElemArr[i].addEventListener("click", function () {

    navbar.classList.toggle("active");
    overlay.classList.toggle("active");
    document.body.classList.toggle("active");

  });

}



/**
 * header sticky
 */

const header = document.querySelector("[data-header]");

window.addEventListener("scroll", function () {

  window.scrollY >= 10 ? header.classList.add("active") : header.classList.remove("active");

});



/**
 * go top
 */

const goTopBtn = document.querySelector("[data-go-top]");

window.addEventListener("scroll", function () {

  window.scrollY >= 500 ? goTopBtn.classList.add("active") : goTopBtn.classList.remove("active");

});

/**
 * preview image
 */

function previewImage(event) {
  const input = event.target;
  const preview = document.getElementById('preview');
  if (input.files && input.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      preview.src = e.target.result;
    }
    reader.readAsDataURL(input.files[0]);
  } else {
    // Se não selecionar nada, volta para a imagem padrão ou original
    if (preview.hasAttribute('data-original')) {
      preview.src = preview.getAttribute('data-original');
    } else {
      preview.src = '/assets/img/sem-foto.png';
    }
  }
}

document.addEventListener('DOMContentLoaded', function () {
  const confirmDeleteButton = document.getElementById('confirmDeleteButton');
  if (confirmDeleteButton) {
    confirmDeleteButton.addEventListener('click', function () {
      const deleteForm = document.getElementById('deleteForm');
      if (deleteForm) {
        deleteForm.submit();
      }
    });
  }
});

function carregarFilmes(page = 0) {
  const inputBusca = document.querySelector('input[name="titulo"]');
  const titulo = inputBusca ? inputBusca.value : '';
  let url = `/api/filmes-paginados?page=${page}&size=5`;
  if (titulo) {
    url += `&titulo=${encodeURIComponent(titulo)}`;
  }
  fetch(url)
    .then(res => res.json())
    .then(data => {
      // Monta a tabela
      const tbody = document.getElementById('filmes-tbody');
      if (tbody) {
        tbody.innerHTML = '';
        data.content.forEach(filme => {
          tbody.innerHTML += `
            <tr>
              <td class="text-start">${filme.titulo}</td>
              <td>${filme.tipo ? filme.tipo.nome : ''}</td>
              <td>${filme.categoria ? filme.categoria.nome : ''}</td>
              <td>${filme.qualidade ? filme.qualidade.nome : ''}</td>
              <td>${filme.duracao}</td>
              <td class="text-center">
                <img src="${filme.foto ? '/upload/' + filme.foto : '/assets/img/sem-foto.png'}"
                     alt="Foto do filme"
                     style="width: 48px; height: 72px; object-fit: cover; border-radius: 4px;">
              </td>
              <td>
                <a class="btn btn-warning btn-sm me-1 d-inline-block" href="/filmes/atualizar/${filme.id}" title="Atualizar">
                  <i class="bi bi-pencil-square"></i>
                </a>
                <a class="btn btn-danger btn-sm d-inline-block" href="/filmes/excluir/${filme.id}" title="Excluir">
                  <i class="bi bi-trash"></i>
                </a>
              </td>
            </tr>
          `;
        });
      }

      // Monta a paginação
      const paginacao = document.getElementById('filmes-paginacao');
      if (paginacao) {
        paginacao.innerHTML = '';

        // Botão anterior
        paginacao.innerHTML += `
          <li class="page-item${data.first ? ' disabled' : ''}">
            <a class="page-link" href="#" onclick="carregarFilmes(${data.number - 1});return false;" aria-label="Anterior">
              <i class="bi bi-chevron-left"></i>
            </a>
          </li>
        `;

        // Botões de página
        for (let i = 0; i < data.totalPages; i++) {
          paginacao.innerHTML += `
            <li class="page-item${i === data.number ? ' active' : ''}">
              <a class="page-link" href="#" onclick="carregarFilmes(${i});return false;">${i + 1}</a>
            </li>
          `;
        }

        // Botão próximo
        paginacao.innerHTML += `
          <li class="page-item${data.last ? ' disabled' : ''}">
            <a class="page-link" href="#" onclick="carregarFilmes(${data.number + 1});return false;" aria-label="Próximo">
              <i class="bi bi-chevron-right"></i>
            </a>
          </li>
        `;
      }
    });
}

// Ao submeter o formulário de busca, previne o submit e chama o JS
document.addEventListener('DOMContentLoaded', function () {
  const formBusca = document.querySelector('form[action="/filmes/busca"]');
  if (formBusca) {
    formBusca.addEventListener('submit', function (e) {
      e.preventDefault();
      carregarFilmes(0);
    });
  }
  if (document.getElementById('filmes-tbody')) {
    carregarFilmes();
  }
});

// Filtro dos lançamentos recentes
document.addEventListener('DOMContentLoaded', function () {
  // Filtro lançamentos
  document.querySelectorAll('.filter-lancamentos').forEach(btn => {
    btn.addEventListener('click', function () {
      document.querySelectorAll('.filter-lancamentos').forEach(b => b.classList.remove('active'));
      this.classList.add('active');

      const tipo = this.getAttribute('data-tipo');
      let url = '/api/lancamentos';
      if (tipo) {
        url += `?tipo=${encodeURIComponent(tipo)}`;
      }
      fetch(url)
        .then(res => res.json())
        .then(filmes => {
          const ul = document.querySelector('.movies-list.has-scrollbar.lancamentos');
          if (ul) {
            ul.innerHTML = '';
            filmes.forEach(filme => {
              ul.innerHTML += `
                <li>
                  <div class="movie-card">
                    <a href="/movie-details/${filme.id}">
                      <figure class="card-banner">
                        <img src="${filme.foto ? '/upload/' + filme.foto : '/assets/img/sem-foto.png'}" alt="${filme.titulo}" />
                      </figure>
                    </a>
                    <div class="title-wrapper">
                      <a href="/movie-details/${filme.id}">
                        <h3 class="card-title">${filme.titulo}</h3>
                      </a>
                      <time>${filme.ano}</time>
                    </div>
                    <div class="card-meta">
                      <div class="badge badge-outline">${filme.qualidade ? filme.qualidade.nome : 'HD'}</div>
                      <div class="duration">
                        <ion-icon name="time-outline"></ion-icon>
                        <time>${filme.duracao} min</time>
                      </div>
                      <div class="rating">
                        <ion-icon name="star"></ion-icon>
                        <data>${filme.avaliacao}</data>
                      </div>
                    </div>
                  </div>
                </li>
              `;
            });
          }
        });
    });
  });

  // Filtro bem avaliados
  document.querySelectorAll('.filter-bem-avaliados').forEach(btn => {
    btn.addEventListener('click', function () {
      document.querySelectorAll('.filter-bem-avaliados').forEach(b => b.classList.remove('active'));
      this.classList.add('active');

      const tipo = this.getAttribute('data-tipo');
      let url = '/api/bem-avaliados';
      if (tipo) {
        url += `?tipo=${encodeURIComponent(tipo)}`;
      }
      fetch(url)
        .then(res => res.json())
        .then(filmes => {
          const ul = document.querySelector('.movies-list.bem-avaliados');
          if (ul) {
            ul.innerHTML = '';
            filmes.forEach(filme => {
              ul.innerHTML += `
                <li>
                  <div class="movie-card">
                    <a href="/movie-details/${filme.id}">
                      <figure class="card-banner">
                        <img src="${filme.foto ? '/upload/' + filme.foto : '/assets/img/sem-foto.png'}" alt="${filme.titulo}" />
                      </figure>
                    </a>
                    <div class="title-wrapper">
                      <a href="/movie-details/${filme.id}">
                        <h3 class="card-title">${filme.titulo}</h3>
                      </a>
                      <time>${filme.ano}</time>
                    </div>
                    <div class="card-meta">
                      <div class="badge badge-outline">${filme.qualidade ? filme.qualidade.nome : 'HD'}</div>
                      <div class="duration">
                        <ion-icon name="time-outline"></ion-icon>
                        <time>${filme.duracao} min</time>
                      </div>
                      <div class="rating">
                        <ion-icon name="star"></ion-icon>
                        <data>${filme.avaliacao}</data>
                      </div>
                    </div>
                  </div>
                </li>
              `;
            });
          }
        });
    });
  });
});

document.addEventListener('DOMContentLoaded', function () {
  // Busca e exibe streamings
  function carregarStreamings(filtroNome = '') {
    fetch('/api/streamings')
      .then(response => response.json())
      .then(streamings => {
        const tbody = document.getElementById('streamings-tbody');
        tbody.innerHTML = '';
        let filtrados = streamings;
        if (filtroNome) {
          filtrados = streamings.filter(s => s.nome.toLowerCase().includes(filtroNome.toLowerCase()));
        }
        if (filtrados.length === 0) {
          tbody.innerHTML = '<tr><td colspan="3">Nenhuma streaming encontrada.</td></tr>';
        } else {
          filtrados.forEach(streaming => {
            tbody.innerHTML += `
              <tr>
                <td class="text-start">${streaming.nome}</td>
                <td class="text-center">
                  ${streaming.foto ? `<img src="/upload/${streaming.foto}" alt="${streaming.nome}" style="max-width:60px;max-height:40px;">` : ''}
                </td>
                <td>
                  <a class="btn btn-warning btn-sm me-1 d-inline-block" href="/streamings/atualizar/${streaming.id}" title="Atualizar">
                    <i class="bi bi-pencil-square"></i>
                  </a>
                  <a class="btn btn-danger btn-sm d-inline-block btn-excluir-streaming" 
                     href="#" 
                     data-id="${streaming.id}" 
                     data-nome="${streaming.nome}" 
                     title="Excluir">
                    <i class="bi bi-trash"></i>
                  </a>
                </td>
              </tr>
            `;
          });

          // Adiciona evento aos botões de excluir para abrir a modal
          document.querySelectorAll('.btn-excluir-streaming').forEach(btn => {
            btn.addEventListener('click', function (e) {
              e.preventDefault();
              const id = this.getAttribute('data-id');
              const nome = this.getAttribute('data-nome');
              document.getElementById('streamingNome').textContent = nome;
              document.getElementById('confirmDeleteButton').href = `/streamings/excluir/${id}`;
              const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
              modal.show();
            });
          });
        }
      });
  }

  // Inicializa a tabela ao carregar a página
  carregarStreamings();

  // Filtro pelo formulário
  const formBusca = document.getElementById('form-busca-streaming');
  if (formBusca) {
    formBusca.addEventListener('submit', function (e) {
      e.preventDefault();
      const valor = document.getElementById('input-busca-streaming').value;
      carregarStreamings(valor);
    });
  }
});

document.addEventListener('DOMContentLoaded', function () {
  const btn = document.getElementById('lang-btn');
  const dropdown = document.getElementById('lang-dropdown');
  const flag = document.getElementById('current-flag');
  btn.addEventListener('click', function (e) {
    e.stopPropagation();
    dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
  });
  document.addEventListener('click', function () {
    dropdown.style.display = 'none';
  });
  document.querySelectorAll('.lang-option').forEach(function (el) {
    el.addEventListener('click', function () {
      flag.src = `https://flagcdn.com/24x18/${el.dataset.flag}.png`;
      // Aqui você pode trocar o idioma da página, recarregar, etc.
      dropdown.style.display = 'none';
    });
  });
});

document.addEventListener('DOMContentLoaded', function () {
  // Limita o campo Ano a 4 dígitos
  const campoAno = document.getElementById('ano');
  if (campoAno) {
    campoAno.addEventListener('input', function () {
      if (this.value.length > 4) {
        this.value = this.value.slice(0, 4);
      }
    });
  }

  // Limita o campo Duração a 3 dígitos
  const campoDuracao = document.getElementById('duracao');
  if (campoDuracao) {
    campoDuracao.addEventListener('input', function () {
      if (this.value.length > 3) {
        this.value = this.value.slice(0, 3);
      }
    });
  }

  // Limita o campo Título a 80 caracteres
  const campoTitulo = document.getElementById('titulo');
  if (campoTitulo) {
    campoTitulo.addEventListener('input', function () {
      if (this.value.length > 80) {
        this.value = this.value.slice(0, 80);
      }
    });
  }

  // Limita o campo Nome da Streaming a 80 caracteres
  const campoNomeStreaming = document.getElementById('nome');
  if (campoNomeStreaming) {
    campoNomeStreaming.addEventListener('input', function () {
      if (this.value.length > 80) {
        this.value = this.value.slice(0, 80);
      }
    });
  }
});

document.addEventListener('DOMContentLoaded', function () {
  // Validação obrigatória para o campo Avaliação
  const formFilme = document.querySelector('form.needs-validation');
  if (formFilme) {
    formFilme.addEventListener('submit', function (e) {
      const avaliacaoInput = document.getElementById('avaliacaoRange');
      if (avaliacaoInput && (!avaliacaoInput.value || Number(avaliacaoInput.value) === 0)) {
        avaliacaoInput.classList.add('is-invalid');
        e.preventDefault();
        e.stopPropagation();
      } else if (avaliacaoInput) {
        avaliacaoInput.classList.remove('is-invalid');
      }
    });
  }
});
