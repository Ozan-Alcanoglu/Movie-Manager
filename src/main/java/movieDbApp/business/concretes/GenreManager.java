package movieDbApp.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import movieDbApp.business.response.GetByNameGenreResponse;
import movieDbApp.coreUtilites.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.GenreService;
import movieDbApp.business.requests.CreateGenreRequests;
import movieDbApp.business.requests.UpdateGenreRequests;
import movieDbApp.business.response.GetAllGenreResponse;
import movieDbApp.business.response.GetByIdGenreResponse;
import movieDbApp.coreUtilites.mappers.ModelMapperService;
import movieDbApp.dataAccess.GenreRepository;
import movieDbApp.entities.Genre;

@Service
@AllArgsConstructor
public class GenreManager implements GenreService{
	
	private GenreRepository genreRepository;
	private ModelMapperService mapperService;
	
	@Override
	public List<GetAllGenreResponse> getAllGenre() {
		
		List<Genre> genres=this.genreRepository.findAll();
		
		List<GetAllGenreResponse> genreResponse=genres.stream().map(genre->this.mapperService.forResponse()
				.map(genre, GetAllGenreResponse.class)).collect(Collectors.toList());
	
		return genreResponse;
	
	}

	@Override
	public GetByIdGenreResponse getGenreById(int id) {
		Genre genre=this.genreRepository.findById(id).orElseThrow();
		
		GetByIdGenreResponse genreResponse=this.mapperService.forResponse().map(genre, GetByIdGenreResponse.class);
		
		return genreResponse;
	}

	@Override
	public GetByNameGenreResponse getGenreByName(String name) {
		Genre genre=this.genreRepository.findByGenre(name);

		if(genre!=null){
			GetByNameGenreResponse getByNameGenreResponse=this.mapperService.forResponse().map(genre, GetByNameGenreResponse.class);
			return getByNameGenreResponse;
		}
		else {
			throw new BusinessException("genre not found");
		}
	}

	@Override
	public void addGenre(CreateGenreRequests createGenreRequests) {
		
		Genre genre=this.mapperService.forRequest().map(createGenreRequests, Genre.class);
		
		this.genreRepository.save(genre);
		
	}

	@Override
	public void updateGenre(int id,UpdateGenreRequests updateGenreRequests) {
		
		Genre genre=this.genreRepository.findById(id).orElseThrow();
		
		genre.setGenre(updateGenreRequests.getGenre());
		
		this.genreRepository.save(genre);
		
	}

	@Override
	public void deleteGenre(int id) {
		
		this.genreRepository.deleteById(id);
		
	}

}
