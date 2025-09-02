// Marcar activo "Mis Proyectos" cuando el hash es #mis-proyectos
document.addEventListener('DOMContentLoaded', () => {
  const link = document.querySelector('[data-link="mis-proyectos"]');
  if (link && window.location.hash === '#mis-proyectos') {
    document.querySelectorAll('.navbar-nav .nav-link').forEach(a => a.classList.remove('active'));
    link.classList.add('active');
  }
});
