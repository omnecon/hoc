import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProfileMySuffix } from './profile-my-suffix.model';
import { ProfileMySuffixPopupService } from './profile-my-suffix-popup.service';
import { ProfileMySuffixService } from './profile-my-suffix.service';

@Component({
    selector: 'jhi-profile-my-suffix-delete-dialog',
    templateUrl: './profile-my-suffix-delete-dialog.component.html'
})
export class ProfileMySuffixDeleteDialogComponent {

    profile: ProfileMySuffix;

    constructor(
        private profileService: ProfileMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.profileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'profileListModification',
                content: 'Deleted an profile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profile-my-suffix-delete-popup',
    template: ''
})
export class ProfileMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profilePopupService: ProfileMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.profilePopupService
                .open(ProfileMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
