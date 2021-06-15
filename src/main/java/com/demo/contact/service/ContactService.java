package com.demo.contact.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.demo.contact.domain.Contact;
import com.demo.contact.domain.FavoriteMovie;
import com.demo.contact.domain.Movie;
import com.demo.contact.repository.FavoriteMovieRepository;
import com.demo.contact.repository.MovieRepository;
import com.demo.contact.util.ContactUtils;
import com.demo.contact.web.ContactController;
import com.demo.contact.web.request.ScoreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.contact.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private FavoriteMovieRepository favoriteMovieRepository;

	public Iterable<Contact> listContacts() {
		Iterable<Contact> list = contactRepository.findAll();
		for (Contact contact : list) {
			contact.add(linkTo(ContactController.class).slash(contact.getContactId()).withSelfRel());
			contact.add(
					linkTo(methodOn(ContactController.class).deleteContact(contact.getContactId())).withRel("delete"));
			contact.add(linkTo(methodOn(ContactController.class).getProfilePicture(contact.getContactId()))
					.withRel("view"));
		}
		return list;
	}

	public Contact addContact(Contact contact) {
		String encodedImage = contact.getProfileImage();
		contact.setProfileImage(null);
		contact.setContactId(null);
		contact.getAddress().setAddressId(null);
		contact = contactRepository.save(contact);
		if (contact != null) {
			ContactUtils.saveImageToServer(ContactUtils.decodeToImage(encodedImage),
					String.valueOf(contact.getContactId()));
		}
		return contact;
	}

	public boolean deleteContact(Long contactId) {
		try {
			contactRepository.delete(contactId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Contact getContactById(Long contactId) {
		Contact contact = contactRepository.findOne(contactId);
		contact.add(
				linkTo(methodOn(ContactController.class).getProfilePicture(contact.getContactId())).withRel("view"));
		contact.add(linkTo(methodOn(ContactController.class).updateContact(null)).withRel("save"));
		return contact;
	}

	public Movie getMovieById(Long movieId) {
		return movieRepository.findOne(movieId);
	}

	public boolean updateContact(Contact contact) {
		String encodedImage = contact.getProfileImage();
		contact.setProfileImage(null);
		contact = contactRepository.save(contact);
		if (contact != null) {
			ContactUtils.saveImageToServer(ContactUtils.decodeToImage(encodedImage),
					String.valueOf(contact.getContactId()));
		}
		return contact != null;
	}

	public Iterable<Contact> listContactsByEmail(String email) {
		Iterable<Contact> list = contactRepository.listContactsByEmail("%" + email.trim().replace(" ", "%") + "%");
		for (Contact contact : list) {
			contact.add(linkTo(ContactController.class).slash(contact.getContactId()).withSelfRel());
			contact.add(
					linkTo(methodOn(ContactController.class).deleteContact(contact.getContactId())).withRel("delete"));
			contact.add(linkTo(methodOn(ContactController.class).getProfilePicture(contact.getContactId()))
					.withRel("view"));
		}
		return list;
	}

	public Iterable<Contact> listContactsByPhoneNumber(String phoneNumber) {
		Iterable<Contact> list = contactRepository
				.listContactsByPhoneNumber("%" + phoneNumber.trim().replace(" ", "%") + "%");
		for (Contact contact : list) {
			contact.add(linkTo(ContactController.class).slash(contact.getContactId()).withSelfRel());
			contact.add(
					linkTo(methodOn(ContactController.class).deleteContact(contact.getContactId())).withRel("delete"));
			contact.add(linkTo(methodOn(ContactController.class).getProfilePicture(contact.getContactId()))
					.withRel("view"));
		}
		return list;
	}

	public Iterable<Contact> listContactsByCity(String city) {
		Iterable<Contact> list = contactRepository.listContactsByCity("%" + city.trim().replace(" ", "%") + "%");
		for (Contact contact : list) {
			contact.add(linkTo(ContactController.class).slash(contact.getContactId()).withSelfRel());
			contact.add(
					linkTo(methodOn(ContactController.class).deleteContact(contact.getContactId())).withRel("delete"));
			contact.add(linkTo(methodOn(ContactController.class).getProfilePicture(contact.getContactId()))
					.withRel("view"));
		}
		return list;
	}

	public Iterable<Contact> listContactsByState(String state) {
		Iterable<Contact> list = contactRepository.listContactsByState("%" + state.trim().replace(" ", "%") + "%");
		for (Contact contact : list) {
			contact.add(linkTo(ContactController.class).slash(contact.getContactId()).withSelfRel());
			contact.add(
					linkTo(methodOn(ContactController.class).deleteContact(contact.getContactId())).withRel("delete"));
			contact.add(linkTo(methodOn(ContactController.class).getProfilePicture(contact.getContactId()))
					.withRel("view"));
		}
		return list;
	}

	public FavoriteMovie scoreMovie(ScoreRequest request) {
		Contact contact = this.getContactById(request.getContactId());
		Movie movie = this.getMovieById(request.getMovieId());
		FavoriteMovie favoriteMovie = new FavoriteMovie(request.getScore(), request.getObservation(), movie, contact);
		favoriteMovie = favoriteMovieRepository.save(favoriteMovie);
		return favoriteMovie;
	}
}
