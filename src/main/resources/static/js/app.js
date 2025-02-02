function showPage(page) {
    const pages = document.querySelectorAll('.page');
    pages.forEach(p => p.classList.remove('active-page'));
    document.getElementById(page).classList.add('active-page');
}

async function logout() {
    console.log('Logout fonksiyonu çağrıldı');
    try {
        console.log('Logout isteği gönderiliyor...');
        const response = await fetch('http://localhost:8080/api/moviedb/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            credentials: 'include'
        });

        console.log('Logout response:', response);

        if (response.ok) {
            console.log('Logout başarılı, yönlendiriliyor...');
            window.location.href = '/moviedb/login';
        } else {
            console.error('Logout işlemi başarısız oldu');
            showMessage('Çıkış işlemi başarısız oldu', 'error');
        }
    } catch (error) {
        console.error('Logout hatası:', error);
        showMessage('Çıkış işlemi sırasında bir hata oluştu', 'error');
    }
} 

function confirmLogout() {
    const confirmAction = confirm("Are you sure?");
    if (confirmAction) {
        window.location.href = '/moviedb/register'; 
    } else {
        console.log("Logout operation cancelled.");
    }
}

function showMessage(message) {
    const messageElement = document.getElementById('message');
    const content = document.getElementById('content');

    content.innerText = message;
    messageElement.classList.add('show');

    setTimeout(() => {
        messageElement.classList.remove('show');
    }, 2000);
}


function loadCategories() {
    fetch('http://localhost:8080/api/moviedb/genres')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network error');
            }
            return response.json();
        })
        .then(categories => {
            console.log(categories);
            const categoryCheckWrapper = document.getElementById('category');
            categoryCheckWrapper.innerHTML = '';

            categories.forEach(category => {
                const label = document.createElement('label');
                label.className = 'form-check-label';
                label.innerHTML = category.genre;

                const checkbox = document.createElement('input');
                checkbox.className = 'form-check-input';
                checkbox.type = 'checkbox';
                checkbox.value = category.genre;

                const div = document.createElement('div');
                div.className = 'form-check';
                div.appendChild(checkbox);
                div.appendChild(label);

                categoryCheckWrapper.appendChild(div);
            });
        })
        .catch(error => {
            console.error('Category loading error:', error);
        });
}


function searchMovie() {
    const searchMovieId = document.getElementById('searchMovieId').value;
    const searchMovieName = document.getElementById('searchMovieName').value.trim().toLowerCase();

    let moviePromises = [];

    if (searchMovieId) {
        moviePromises.push(
            fetch(`/api/moviedb/movies/getbyid/${searchMovieId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Movie not found');
                    }
                    return response.json();
                })
                .then(movie => [movie])
                .catch(error => {
                    console.error('Error fetching movie by ID:', error);
                })
        );
    }

    if (searchMovieName) {
        moviePromises.push(
            fetch(`/api/moviedb/movies/getname/${searchMovieName}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Movies not found');
                    }
                    return response.json();
                })
                .then(movies => movies)
                .catch(error => {
                    console.error('Error fetching movies by name:', error);
                })
        );
    }

    Promise.all(moviePromises)
        .then(results => {
            const allMovies = results.flat();
            if (allMovies.length === 0) {
                console.log('No movies found.');
                updateMovieCards([]);
            } else {
                updateMovieCards(allMovies);
            }
        })
        .catch(error => {
            console.error('Error fetching movies:', error);
        });
}

function addMovie() {
	const title = document.getElementById('movieTitle').value;
	const year = document.getElementById('movieYear').value;
	const directors = document.getElementById('addmovieDirectors').value.split(',').map(d => d.trim());
	const actors = document.getElementById('addmovieActors').value.split(',').map(a => a.trim());
	const castList = directors.concat(actors);
	const image = document.getElementById('movieImage').value;
	const categories = Array.from(document.querySelectorAll('#category input[type="checkbox"]:checked')).map(checkbox => checkbox.value);

	const dateObj = new Date(year);
	const cmonth = dateObj.getUTCMonth() + 1;
	const cday = dateObj.getUTCDate();
	const cyear = dateObj.getUTCFullYear();

	const newDate = `${cyear}-${cmonth.toString().padStart(2, '0')}-${cday.toString().padStart(2, '0')}`;
	const newDatePost = new Date(newDate);

	const movieData = { name: title, date: newDatePost, imageurl: image };

	fetch('http://localhost:8080/api/moviedb/movies/add', {
	    method: 'POST',
	    headers: {
	        'Content-Type': 'application/json'
	    },
	    body: JSON.stringify(movieData)
	})
	.then(() => {
	    castList.forEach(cast => {
	        const movieCrewData = { movie: title, crew: cast };

	        fetch('http://localhost:8080/api/moviedb/moviecrew/add', {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            body: JSON.stringify(movieCrewData)
	        })
	        .then(response => response.json())
	        .then(data => {
	            console.log('Movie-Crew successfully added:', data);
	        })
	        .catch(error => {
	            console.error('An error occurred while adding the movie-crew:', error);
	        });
	    });             
	
		    categories.forEach(category => {
		        const movieGenreData = { movie: title, genre: category };

		        fetch('http://localhost:8080/api/moviedb/moviegenre/add', {
		            method: 'POST',
		            headers: {
		                'Content-Type': 'application/json'
		            },
		            body: JSON.stringify(movieGenreData)
		        })
		        .then(response => response.json())
		        .then(data => {
		            console.log('Movie-Genre successfully added:', data);
		        })
		        .catch(error => {
		            console.error('An error occurred while adding the movie-genre:', error);
		        });
		    });
		
	});


	

    const directorRoleId = 2;
    const actorRoleId = 1;

    if (directorRoleId && actorRoleId) {
		
        directors.forEach(director => {
            const directorData = {
                name: director,
                id: directorRoleId,
            };

            fetch('http://localhost:8080/api/moviedb/crews/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(directorData)
            })
                
        });

        actors.forEach(actor => {
            const actorData = {
                name: actor,
                id: actorRoleId,
            };

            fetch('http://localhost:8080/api/moviedb/crews/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(actorData)
            })
                
        });
        showMessage("Movie added successfully");

    } else {
        console.error('Actor and Director role IDs not found.');
    }
	document.getElementById('addMovieForm').reset();
}

function updateMovie() {
    const movieId = document.getElementById('updateMovieId').value;
    const newTitle = document.getElementById('updateMovieTitle').value;
    const newYear = document.getElementById('updateMovieYear').value;
    const newImageUrl = document.getElementById('updateMovieImage').value;

    const dateObj = new Date(newYear);
    const cmonth = dateObj.getUTCMonth() + 1;
    const cday = dateObj.getUTCDate();
    const cyear = dateObj.getUTCFullYear();

    const newDate = `${cyear}-${cmonth.toString().padStart(2, '0')}-${cday.toString().padStart(2, '0')}`;
    const newDatePost = new Date(newDate);

    const updatedMovieData = {
        name: newTitle,
        date: newDatePost,
        imageurl: newImageUrl
    };

    fetch(`http://localhost:8080/api/moviedb/movies/update/${movieId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedMovieData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('An error occurred while updating movie information');
            }
        })
        .then(updatedMovie => {
            console.log('Movie updated successfully', updatedMovie);
            document.getElementById('updateMovieForm').reset();
            showMessage("Movie updated successfully")
        });

}

function updateCrewMembers() {

    const castId = document.getElementById('updateCastId')?.value;
    const directors = document.getElementById('updateDirectors')?.value.split(',').map(d => d.trim());
    const actors = document.getElementById('updateActors')?.value.split(',').map(a => a.trim());


    if (directors && directors.length > 0) {
        directors.forEach(director => {
            const directorData = {
                name: director
            };

            fetch(`http://localhost:8080/api/moviedb/crews/update/${castId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(directorData)
            })
                .then(response => response.json())
                .then(directorData => {
                    console.log('Director updated successfully', directorData);
                    document.getElementById('updateCastForm').reset();
                    showMessage("Cast updated successfully")
                });
        });
    }

    if (actors && actors.length > 0) {
        actors.forEach(actor => {
            const actorData = {
                name: actor
            };

            fetch(`http://localhost:8080/api/moviedb/crews/update/${castId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(actorData)
            })
                .then(response => response.json())
                .then(actorData => {
                    console.log('Actor updated successfully', actorData);
                    document.getElementById('updateCastForm').reset();
                    showMessage("Cast updated successfully")
                });
        });
    }

}

function deleteMovie() {
    const movieId = document.getElementById('deleteMovieId').value;

    if (!movieId) {
        showMessage('Please enter a valid movie ID.');
        return;
    }

    fetch(`http://localhost:8080/api/moviedb/movies/delete/${movieId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete the movie.');
            }
        })
        .then(data => {
            showMessage('Movie deleted successfully');
            document.getElementById('deleteMovieForm').reset();
        })

}

document.addEventListener('DOMContentLoaded', function () {
    loadCategories();

    document.getElementById('addMovieForm').addEventListener('submit', function(event) {
        event.preventDefault();
		addMovie()  
		       
    });

    document.getElementById('updateCastForm').addEventListener('submit', function(event) {
        event.preventDefault();
        updateCrewMembers();
    });

    document.getElementById('updateMovieForm').addEventListener('submit', function(event) {
        event.preventDefault();
        updateMovie();
    });

    document.getElementById('deleteMovieForm').addEventListener('submit', function(event) {
        event.preventDefault();
        deleteMovie();
    });
});
