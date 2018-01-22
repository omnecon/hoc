/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { BannerMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix-dialog.component';
import { BannerMySuffixService } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.service';
import { BannerMySuffix } from '../../../../../../main/webapp/app/entities/banner-my-suffix/banner-my-suffix.model';

describe('Component Tests', () => {

    describe('BannerMySuffix Management Dialog Component', () => {
        let comp: BannerMySuffixDialogComponent;
        let fixture: ComponentFixture<BannerMySuffixDialogComponent>;
        let service: BannerMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [BannerMySuffixDialogComponent],
                providers: [
                    BannerMySuffixService
                ]
            })
            .overrideTemplate(BannerMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BannerMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BannerMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BannerMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.banner = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bannerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BannerMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.banner = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bannerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
