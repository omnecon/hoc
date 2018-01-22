import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ProfileMySuffix } from './profile-my-suffix.model';
import { ProfileMySuffixService } from './profile-my-suffix.service';

@Injectable()
export class ProfileMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private profileService: ProfileMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.profileService.find(id).subscribe((profile) => {
                    profile.createdDate = this.datePipe
                        .transform(profile.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    profile.lastUpdatedDate = this.datePipe
                        .transform(profile.lastUpdatedDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.profileModalRef(component, profile);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.profileModalRef(component, new ProfileMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    profileModalRef(component: Component, profile: ProfileMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.profile = profile;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
