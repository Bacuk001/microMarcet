import {Component, Inject} from '@angular/core';
import {IAuthenticationService} from '../services/authentication/iauthentication.service';
import {User} from '../entities/user';

/**
 * Component responsible for logging in to the system.
 */
@Component({
  selector: 'app-right-navigate',
  templateUrl: './right-navigate.component.html',
  styleUrls: ['./right-navigate.component.css']
})
export class RightNavigateComponent {
  /**
   * User registered in the system.
   */
  public user: User;
  /**
   * System messages for the user.
   */
  public message


  constructor(@Inject('authenticationService') public authService: IAuthenticationService) {
  }

  /**
   * The method is executed when you press the login.
   */
  loginSystem() {
    this.authService.loginSystem()
      .then(resp => this.user = resp)
      .catch(error => this.message = error);
  }

  /**
   * The method is executed when you exit the system.
   */
  logOut() {
    this.authService.logOut();
  }
}
