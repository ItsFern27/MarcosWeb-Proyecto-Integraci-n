import { supabase } from './supabaseClient.js'

async function cargarUsuarios() {
  const { data, error } = await supabase
    .from('usuarios')
    .select('*')

  if (error) {
    console.error('Error al cargar usuarios:', error)
  } else {
    console.log('Usuarios:', data)
  }
}

cargarUsuarios()
