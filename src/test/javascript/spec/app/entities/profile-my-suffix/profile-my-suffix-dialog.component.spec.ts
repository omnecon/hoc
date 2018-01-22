/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { ProfileMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix-dialog.component';
import { ProfileMySuffixService } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix.service';
import { ProfileMySuffix } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix.model';
import { FeatureMySuffixService } from '../../../../../../main/webapp/app/entities/feature-my-suffix';

describe('Component Tests', () => {

    describe('ProfileMySuffix Management Dialog Component', () => {
        let comp: ProfileMySuffixDialogComponent;
        let fixture: ComponentFixture<ProfileMySuffixDialogComponent>;
        let service: ProfileMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [ProfileMySuffixDialogComponent],
                providers: [
                    FeatureMySuffixService,
                    ProfileMySuffixService
                ]
            })
            .overrideTemplate(ProfileMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfileMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfileMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProfileMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.profile = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'profileListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ProfileMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.profile = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'profileListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
