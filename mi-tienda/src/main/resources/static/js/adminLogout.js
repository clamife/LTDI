function logout() {
    fetch("/logout", { method: 'POST' })
      .then(response => window.location.href = "/"); 
  }