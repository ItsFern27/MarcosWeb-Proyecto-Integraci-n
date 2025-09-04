// Marcar activo "Mis Proyectos" cuando el hash es #mis-proyectos
document.addEventListener('DOMContentLoaded', () => {
  const link = document.querySelector('[data-link="mis-proyectos"]');
  if (link && window.location.hash === '#mis-proyectos') {
    document.querySelectorAll('.navbar-nav .nav-link').forEach(a => a.classList.remove('active'));
    link.classList.add('active');
  }
});

const projectModal = new bootstrap.Modal(document.getElementById('projectModal'));

function openModal(project) {
    document.getElementById('modalTitle').textContent = project.name;
    document.getElementById('modalDescription').textContent = project.description;
    document.getElementById('modalAuthor').textContent = project.author;
    document.getElementById('modalAuthorRole').textContent = project.role;
    document.getElementById('modalStatus').textContent = project.status;
    document.getElementById('modalDuration').textContent = `Duración: ${project.duration}`;

    const statusElement = document.getElementById('modalStatus');
    statusElement.className = 'badge';
    switch(project.status.toLowerCase()) {
        case 'en desarrollo':
            statusElement.classList.add('bg-warning');
            break;
        case 'beta':
        case 'beta privada':
            statusElement.classList.add('bg-info');
            break;
        case 'producción':
        case 'lanzamiento':
            statusElement.classList.add('bg-success');
            break;
        case 'investigación':
            statusElement.classList.add('bg-purple');
            break;
        default:
            statusElement.classList.add('bg-secondary');
    }

    const membersContainer = document.getElementById('modalMembers');
    membersContainer.innerHTML = '';
    
    project.members.forEach(member => {
        const memberDiv = document.createElement('div');
        memberDiv.className = 'd-flex align-items-center gap-3 p-3 bg-light rounded-3 mb-2';
        memberDiv.innerHTML = `
            <div class="bg-secondary rounded-circle d-flex align-items-center justify-content-center" style="width: 40px; height: 40px;">
                <svg class="text-white" width="20" height="20" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                </svg>
            </div>
            <div>
                <p class="fw-medium text-dark mb-0">${member.name}</p>
                <p class="small text-muted mb-0">${member.role}</p>
            </div>
        `;
        membersContainer.appendChild(memberDiv);
    });

    const techContainer = document.getElementById('modalTechnologies');
    techContainer.innerHTML = '';
    
    const techColors = {
        'React': 'bg-primary',
        'Vue.js': 'bg-success',
        'Angular': 'bg-danger',
        'Node.js': 'bg-success',
        'Python': 'bg-warning',
        'JavaScript': 'bg-warning',
        'TypeScript': 'bg-primary',
        'Flutter': 'bg-info',
        'MongoDB': 'bg-success',
        'PostgreSQL': 'bg-primary',
        'Firebase': 'bg-warning',
        'AWS': 'bg-warning',
        'Docker': 'bg-primary',
        'GraphQL': 'bg-purple',
        'TensorFlow': 'bg-warning',
        'Django': 'bg-success',
        'Express': 'bg-secondary',
        'Stripe': 'bg-purple',
        'D3.js': 'bg-warning',
        'Pandas': 'bg-primary',
        'Scikit-learn': 'bg-warning',
        'Redis': 'bg-danger',
        'Dart': 'bg-primary'
    };

    project.technologies.forEach(tech => {
        const techSpan = document.createElement('span');
        const colorClass = techColors[tech] || 'bg-secondary';
        techSpan.className = `badge ${colorClass} px-3 py-2`;
        techSpan.textContent = tech;
        techContainer.appendChild(techSpan);
    });

    projectModal.show();
}

const saveProjectBtn = document.getElementById('saveProjectBtn');
if (saveProjectBtn) {
    saveProjectBtn.addEventListener('click', function() {
        if (this.textContent.includes('Guardar')) {
            this.textContent = '✅ Guardado';
            this.classList.remove('btn-outline-secondary');
            this.classList.add('btn-success');
        } else {
            this.textContent = '⭐ Guardar';
            this.classList.remove('btn-success');
            this.classList.add('btn-outline-secondary');
        }
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const projectCards = document.querySelectorAll('.project-card');
    
    projectCards.forEach(card => {
        card.addEventListener('click', function() {
            const projectData = JSON.parse(this.getAttribute('data-project'));
            openModal(projectData);
        });
    });

    const filterButtons = document.querySelectorAll('.btn-outline-secondary, .btn-primary');
    
    filterButtons.forEach(button => {
        button.addEventListener('click', function() {
            filterButtons.forEach(btn => {
                btn.classList.remove('btn-primary');
                btn.classList.add('btn-outline-secondary');
            });
            
            this.classList.remove('btn-outline-secondary');
            this.classList.add('btn-primary');
        });
    });
});


/* seccion Mis proyectos */
function showMyProjects() {
  document.getElementById("homePage").classList.add("d-none");
  document.getElementById("myProjectsPage").classList.remove("d-none");

  // marcar activo en el nav
  document.querySelectorAll(".navbar-nav .nav-link").forEach(a => a.classList.remove("active"));
  const link = document.querySelector('[data-link="mis-proyectos"]');
  if (link) link.classList.add("active");
}

function showHomePage() {
  document.getElementById("myProjectsPage").classList.add("d-none");
  document.getElementById("homePage").classList.remove("d-none");

  // quitar activo
  document.querySelectorAll(".navbar-nav .nav-link").forEach(a => a.classList.remove("active"));
}